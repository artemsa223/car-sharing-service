package carsharing.dto.rental;

import java.time.LocalDate;

public record CreateRentalRequestDto(
        Long carId,
        LocalDate rentalDate,
        LocalDate returnDate
) {
}
