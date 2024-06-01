package carsharing.user.mapper;

import carsharing.auth.config.MapperConfig;
import carsharing.auth.dto.UserRegistrationRequestDto;
import carsharing.auth.dto.UserResponseDto;
import carsharing.exception.EntityNotFoundException;
import carsharing.user.model.Role;
import carsharing.user.model.User;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationRequestDto registrationRequestDto);

    @Mapping(target = "role", source = "user.roles")
    UserResponseDto toDto(User user);

    default Role.RoleName getRole(Set<Role> roles) {
        return roles.stream()
                .findAny()
                .map(Role::getName)
                .orElseThrow(() -> new EntityNotFoundException("Can't find role"));
    }
}
