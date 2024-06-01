package carsharing.rental.dto;

import java.time.LocalDate;

public record CreateRentalRequestDto(
        Long carId,
        LocalDate rentalDate,
        LocalDate returnDate
) {
}
