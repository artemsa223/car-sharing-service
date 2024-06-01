package carsharing.user.service;

import carsharing.auth.dto.UserRegistrationRequestDto;
import carsharing.auth.dto.UserResponseDto;
import carsharing.exception.RegistrationException;
import carsharing.user.dto.UpdateRole;
import carsharing.user.dto.UserUpdateDto;
import carsharing.user.model.User;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationRequestDto requestDto)
            throws RegistrationException;

    void deleteById(Long id);

    User getById(Long id);

    UserResponseDto getUserDtoById(Long id);

    UserResponseDto updateUserRole(Long id, UpdateRole userUpdateDto);

    UserResponseDto updateUser(Long id, UserUpdateDto userUpdateDto);

}
