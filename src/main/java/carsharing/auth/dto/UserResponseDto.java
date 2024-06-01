package carsharing.auth.dto;

import carsharing.user.model.Role;

public record UserResponseDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        Role.RoleName role
) {
}
