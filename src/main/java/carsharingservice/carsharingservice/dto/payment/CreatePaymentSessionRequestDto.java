package carsharingservice.carsharingservice.dto.payment;

import carsharingservice.carsharingservice.model.Payment;

public record CreatePaymentSessionRequestDto(
        Long rentalId,
        Payment.PaymentType paymentType
) {
}
