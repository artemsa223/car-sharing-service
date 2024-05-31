package carsharing.dto.rental;

import java.time.LocalDate;

public record SetActualReturnDateRequestDto(
        LocalDate actualReturnDate
) {
}
