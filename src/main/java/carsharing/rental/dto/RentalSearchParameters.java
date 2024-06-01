package carsharing.rental.dto;

public record RentalSearchParameters(
        Long[] user_id,
        Boolean is_active
) {
}
