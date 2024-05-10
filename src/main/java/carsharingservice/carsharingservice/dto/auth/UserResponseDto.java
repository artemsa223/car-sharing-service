package carsharingservice.carsharingservice.dto.auth;

import carsharingservice.carsharingservice.model.Role;

public record UserResponseDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        Role.RoleName role
) {
}
