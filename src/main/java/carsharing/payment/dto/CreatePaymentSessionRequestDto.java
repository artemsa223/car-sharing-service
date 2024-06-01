package carsharing.payment.dto;

import carsharing.payment.model.Payment;

public record CreatePaymentSessionRequestDto(
        Long rentalId,
        Payment.PaymentType paymentType
) {
}
