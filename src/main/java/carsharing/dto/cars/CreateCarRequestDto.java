package carsharing.dto.cars;

import carsharing.model.Type;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CreateCarRequestDto(
        @NotNull
        String model,
        @NotNull
        String brand,
        @NotNull
        Type type,
        @Positive
        int inventoryNumber,
        @Positive
        BigDecimal fee
) {
}