package carsharing.dto.cars;

import carsharing.model.Type;
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
