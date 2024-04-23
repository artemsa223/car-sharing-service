package carsharingservice.carsharingservice.service.user;

import carsharingservice.carsharingservice.dto.auth.UserRegistrationRequestDto;
import carsharingservice.carsharingservice.dto.auth.UserResponseDto;
import carsharingservice.carsharingservice.exception.RegistrationException;
import carsharingservice.carsharingservice.models.User;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationRequestDto requestDto)
            throws RegistrationException;

    void deleteById(Long id);

    User getById(Long id);
}
