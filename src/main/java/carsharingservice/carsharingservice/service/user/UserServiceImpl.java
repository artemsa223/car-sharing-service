package carsharingservice.carsharingservice.service.user;

import carsharingservice.carsharingservice.dto.auth.UserRegistrationRequestDto;
import carsharingservice.carsharingservice.dto.auth.UserResponseDto;
import carsharingservice.carsharingservice.dto.user.UpdateRole;
import carsharingservice.carsharingservice.dto.user.UserUpdateDto;
import carsharingservice.carsharingservice.exception.EntityNotFoundException;
import carsharingservice.carsharingservice.exception.RegistrationException;
import carsharingservice.carsharingservice.mapper.UserMapper;
import carsharingservice.carsharingservice.model.Role;
import carsharingservice.carsharingservice.model.User;
import carsharingservice.carsharingservice.repository.role.RoleRepository;
import carsharingservice.carsharingservice.repository.user.UserRepository;
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
