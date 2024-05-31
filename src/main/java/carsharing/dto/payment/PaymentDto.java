package carsharing.dto.payment;

import carsharing.model.Payment;
import java.math.BigDecimal;

public record PaymentDto(
        Payment.PaymentStatus status,
        Payment.PaymentType type,
        Long rentalId,
        String sessionUrl,
        String sessionId,
        BigDecimal amount
) {
}
