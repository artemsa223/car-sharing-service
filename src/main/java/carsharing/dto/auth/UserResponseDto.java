package carsharing.dto.auth;

import carsharing.model.Role;

public record UserResponseDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        Role.RoleName role
) {
}
