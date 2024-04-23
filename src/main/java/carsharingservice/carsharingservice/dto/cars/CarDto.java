package carsharingservice.carsharingservice.dto.cars;

import carsharingservice.carsharingservice.models.Type;
import java.math.BigDecimal;

public record CarDto(
        Long id,
        String model,
        String brand,
        Type type,
        int inventoryNumber,
        BigDecimal fee
) {
}
