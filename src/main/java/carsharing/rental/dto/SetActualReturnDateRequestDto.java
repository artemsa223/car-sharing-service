package carsharing.rental.dto;

import java.time.LocalDate;

public record SetActualReturnDateRequestDto(
        LocalDate actualReturnDate
) {
}
