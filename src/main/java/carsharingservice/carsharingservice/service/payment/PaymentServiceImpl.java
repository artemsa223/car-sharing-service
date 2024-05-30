package carsharingservice.carsharingservice.service.payment;

import static com.stripe.param.checkout.SessionCreateParams.LineItem;
import static com.stripe.param.checkout.SessionCreateParams.Mode;
import static com.stripe.param.checkout.SessionCreateParams.PaymentMethodOptions.AcssDebit.Currency;
import static com.stripe.param.checkout.SessionCreateParams.PaymentMethodType;

import carsharingservice.carsharingservice.dto.payment.CancelPaymentDto;
import carsharingservice.carsharingservice.dto.payment.CreatePaymentSessionRequestDto;
import carsharingservice.carsharingservice.dto.payment.PaymentDto;
import carsharingservice.carsharingservice.exception.EntityNotFoundException;
import carsharingservice.carsharingservice.exception.PaymentException;
import carsharingservice.carsharingservice.mapper.PaymentMapper;
import carsharingservice.carsharingservice.model.Payment;
import carsharingservice.carsharingservice.model.Rental;
import carsharingservice.carsharingservice.repository.payment.PaymentDao;
import carsharingservice.carsharingservice.repository.payment.PaymentRepository;
import carsharingservice.carsharingservice.repository.rental.RentalRepository;
import carsharingservice.carsharingservice.service.notification.NotificationService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.net.URI;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private static final BigDecimal FINE_MULTIPLIER = BigDecimal.valueOf(1.75);
    private static final String SUCCESS_URL = "http://localhost:8080/payments/success?rentalId=";
    private static final String CANCEL_URL = "http://localhost:8080/payments/cancel?rentalId=";
    private static final String CANCEL_MESSAGE = """ 
            Payment was cancelled, you can try again
            with the same link, for next 24 hours""";
    @Value("${stripe.secret.key}")
    private String stripeApiKey;

    private final RentalRepository rentalRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationService notificationService;
    private final PaymentDao paymentDao;

    @Override
    @Transactional
    public PaymentDto createPaymentSession(CreatePaymentSessionRequestDto request) {

        Rental rental = rentalRepository.findById(request.rentalId())
                .orElseThrow(() -> new IllegalArgumentException("Rental not found"));
        checkIfRentalIsPaid(rental);
        long moneyToPay = calculateTotalPrice(request);
        Stripe.apiKey = stripeApiKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(PaymentMethodType.CARD)
                .setMode(Mode.PAYMENT)
                .setSuccessUrl(String.valueOf(URI.create(SUCCESS_URL + rental.getId())))
                .setCancelUrl(String.valueOf(URI.create(CANCEL_URL + rental.getId())))
                .addAllLineItem(Collections.singletonList(LineItem.builder()
                                .setPriceData(
                                        LineItem.PriceData.builder()
                                                .setCurrency(Currency.USD.getValue())
                                                .setUnitAmount(moneyToPay
                                                        * 100L)
                                                .setProductData(
                                                        LineItem.PriceData.ProductData.builder()
                                                                .setName("Payment for "
                                                                        + "rental  of the car"
                                                                        + rental)
                                                                .build()
                                                )
                                                .build()
                                )
                                .setQuantity(1L)
                                .build()
                )).build();
        Session session;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create Stripe session", e);
        }

        Payment payment = new Payment();
        payment.setSessionId(session.getId());
        payment.setSessionUrl(session.getUrl());
        payment.setPaymentStatus(Payment.PaymentStatus.PENDING);
        payment.setPaymentType(request.paymentType());
        payment.setRental(findById(request.rentalId()));
        payment.setTotalPrice(BigDecimal.valueOf(moneyToPay));

        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    public CancelPaymentDto cancelPayment(Long rentalId) {
        return new CancelPaymentDto(CANCEL_MESSAGE);
    }

    @Override
    public List<PaymentDto> getAllPaymentsByUserId(Long userId) {
        AtomicReference<List<Rental>> userRentals =
                new AtomicReference<>(rentalRepository.findAllByUserId(userId));
        List<Payment> userPayments = paymentDao.findAllByRentalsId(userRentals.get()
                .stream()
                .map(Rental::getId)
                .toList()
        );
        return userPayments.stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public PaymentDto checkSuccessfulPaymentByRentalId(Long rentalId) {
        Payment payment = paymentRepository.findByRentalId(rentalId).orElseThrow(() ->
                new EntityNotFoundException("Payment not found"));
        Session session;
        try {
            session = Session.retrieve(payment.getSessionId());
        } catch (StripeException e) {
            throw new RuntimeException("Failed to retrieve session from Stripe", e);
        }
        if (session.getStatus().equals("complete")) {
            payment.setPaymentStatus(Payment.PaymentStatus.PAID);
            paymentRepository.save(payment);

            String notificationMessage = sendTelegramMassage(payment);
            notificationService.sendNotification(notificationMessage);

            return paymentMapper.toDto(payment);
        }
        throw new PaymentException("Payment is not completed");
    }

    private String sendTelegramMassage(Payment payment) {
        return "Payment for rental of the car "
                + payment.getRental().getCar().getModel()
                + " was successful";
    }

    private long calculateTotalPrice(CreatePaymentSessionRequestDto request) {
        Optional<Rental> rental = rentalRepository.findById(request.rentalId());
        long numberOfRentalDays = ChronoUnit.DAYS.between(
                rental.get().getRentalDate(), rental.get().getActualReturnDate());
        if (numberOfRentalDays == 0) {
            numberOfRentalDays = 1;
        }
        BigDecimal dailyFee = rental.get().getCar().getFee();

        long fine = calculateFine(rental.get(), numberOfRentalDays);
        return dailyFee.multiply(BigDecimal.valueOf(numberOfRentalDays))
                .multiply(BigDecimal.valueOf(100))
                .add(BigDecimal.valueOf(fine))
                .longValue();
    }

    private long calculateFine(Rental rental, long numberOfRentalDays) {
        if (rental.getActualReturnDate().isAfter(rental.getReturnDate())) {
            return rental.getCar().getFee().multiply(BigDecimal.valueOf(numberOfRentalDays))
                    .multiply(FINE_MULTIPLIER)
                    .multiply(BigDecimal.valueOf(100))
                    .longValue();
        }
        return 0;
    }

    private void checkIfRentalIsPaid(Rental rental) {
        paymentRepository.findSuccessfulPaymentByRentalId(rental.getId())
                .ifPresent(payment -> {
                    throw new IllegalArgumentException("Rental is already paid");
                });
    }

    private Rental findById(Long rentalId) {
        return rentalRepository.findById(rentalId).orElseThrow(() ->
                new EntityNotFoundException("Rental not found"));
    }

}
