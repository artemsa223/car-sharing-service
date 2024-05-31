package carsharing.service.payment;

import carsharing.dto.payment.CancelPaymentDto;
import carsharing.dto.payment.CreatePaymentSessionRequestDto;
import carsharing.dto.payment.PaymentDto;
import java.util.List;

public interface PaymentService {
    PaymentDto createPaymentSession(CreatePaymentSessionRequestDto request);

    List<PaymentDto> getAllPaymentsByUserId(Long userId);

    List<PaymentDto> getAllPayments();

    PaymentDto checkSuccessfulPaymentByRentalId(Long rentalId);

    CancelPaymentDto cancelPayment(Long rentalId);

}
