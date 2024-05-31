package carsharing.dto.user;

public record UserDto(
        Long id,
        String firstName,
        String lastName,
        String email
) {
}
