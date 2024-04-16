package carsharingservice.carsharingservice.service;

import carsharingservice.carsharingservice.dto.UserRegistrationRequestDto;
import carsharingservice.carsharingservice.dto.UserResponseDto;
import carsharingservice.carsharingservice.exception.RegistrationException;
import carsharingservice.carsharingservice.models.User;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationRequestDto requestDto)
            throws RegistrationException;

    void deleteById(Long id);

    User getById(Long id);
}
