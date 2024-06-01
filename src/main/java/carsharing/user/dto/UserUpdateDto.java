package carsharing.user.dto;

public record UserUpdateDto(
        String firstName,
        String lastName,
        String email
) {
}
