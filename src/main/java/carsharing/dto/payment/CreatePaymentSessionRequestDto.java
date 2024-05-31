package carsharing.dto.payment;

import carsharing.model.Payment;

public record CreatePaymentSessionRequestDto(
        Long rentalId,
        Payment.PaymentType paymentType
) {
}
