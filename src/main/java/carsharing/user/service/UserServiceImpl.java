package carsharing.user.service;

import carsharing.auth.dto.UserRegistrationRequestDto;
import carsharing.auth.dto.UserResponseDto;
import carsharing.exception.EntityNotFoundException;
import carsharing.exception.RegistrationException;
import carsharing.user.dto.UpdateRole;
import carsharing.user.dto.UserUpdateDto;
import carsharing.user.mapper.UserMapper;
import carsharing.user.model.Role;
import carsharing.user.model.User;
import carsharing.user.repository.RoleRepository;
import carsharing.user.repository.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto registerUser(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException(
                    "Can't register user - user with this email already exists!");
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRepeatPassword(user.getPassword());
        Role role = roleRepository.findByName(Role.RoleName.ROLE_CUSTOMER);
        user.setRoles(Set.of(role));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find user by id=" + id));
    }

    @Override
    public UserResponseDto getUserDtoById(Long id) {
        return userMapper.toDto(getById(id));
    }

    @Override
    public UserResponseDto updateUserRole(Long id, UpdateRole userUpdateDto) {
        User user = getById(id);
        Role.RoleName role = Role.RoleName.valueOf("ROLE_" + userUpdateDto.role());
        user.setRoles(Set.of(roleRepository.findByName(role)));
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserUpdateDto userUpdateDto) {
        User user = getById(id);
        user.setFirstName(userUpdateDto.firstName());
        user.setLastName(userUpdateDto.lastName());
        user.setEmail(userUpdateDto.email());
        return userMapper.toDto(userRepository.save(user));
    }
}
