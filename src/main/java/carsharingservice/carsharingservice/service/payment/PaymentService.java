package carsharingservice.carsharingservice.service.payment;

import carsharingservice.carsharingservice.dto.payment.CancelPaymentDto;
import carsharingservice.carsharingservice.dto.payment.CreatePaymentSessionRequestDto;
import carsharingservice.carsharingservice.dto.payment.PaymentDto;
import java.util.List;

public interface PaymentService {
    PaymentDto createPaymentSession(CreatePaymentSessionRequestDto request);

    List<PaymentDto> getAllPaymentsByUserId(Long userId);

    List<PaymentDto> getAllPayments();

    PaymentDto checkSuccessfulPaymentByRentalId(Long rentalId);

    CancelPaymentDto cancelPayment(Long rentalId);

}
