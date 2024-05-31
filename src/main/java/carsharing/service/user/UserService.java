package carsharing.service.user;

import carsharing.dto.auth.UserRegistrationRequestDto;
import carsharing.dto.auth.UserResponseDto;
import carsharing.dto.user.UpdateRole;
import carsharing.dto.user.UserUpdateDto;
import carsharing.exception.RegistrationException;
import carsharing.model.User;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationRequestDto requestDto)
            throws RegistrationException;

    void deleteById(Long id);

    User getById(Long id);

    UserResponseDto getUserDtoById(Long id);

    UserResponseDto updateUserRole(Long id, UpdateRole userUpdateDto);

    UserResponseDto updateUser(Long id, UserUpdateDto userUpdateDto);

}
