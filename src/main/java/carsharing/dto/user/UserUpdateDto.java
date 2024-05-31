package carsharing.dto.user;

public record UserUpdateDto(
        String firstName,
        String lastName,
        String email
) {
}
