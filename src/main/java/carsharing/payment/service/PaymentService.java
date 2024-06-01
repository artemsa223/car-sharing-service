package carsharing.payment.service;

import carsharing.payment.dto.CancelPaymentDto;
import carsharing.payment.dto.CreatePaymentSessionRequestDto;
import carsharing.payment.dto.PaymentDto;
import java.util.List;

public interface PaymentService {
    PaymentDto createPaymentSession(CreatePaymentSessionRequestDto request);

    List<PaymentDto> getAllPaymentsByUserId(Long userId);

    List<PaymentDto> getAllPayments();

    PaymentDto checkSuccessfulPaymentByRentalId(Long rentalId);

    CancelPaymentDto cancelPayment(Long rentalId);

}
