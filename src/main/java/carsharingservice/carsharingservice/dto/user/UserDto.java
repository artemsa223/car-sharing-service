package carsharingservice.carsharingservice.dto.user;

public record UserDto(
        Long id,
        String firstName,
        String lastName,
        String email
) {
}