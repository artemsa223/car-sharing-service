package carsharingservice.carsharingservice.service;

import carsharingservice.carsharingservice.dto.UserRegistrationRequestDto;
import carsharingservice.carsharingservice.dto.UserResponseDto;
import carsharingservice.carsharingservice.exception.EntityNotFoundException;
import carsharingservice.carsharingservice.exception.RegistrationException;
import carsharingservice.carsharingservice.mapper.UserMapper;
import carsharingservice.carsharingservice.models.Role;
import carsharingservice.carsharingservice.models.User;
import carsharingservice.carsharingservice.repository.RoleRepository;
import carsharingservice.carsharingservice.repository.UserRepository;
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
}
