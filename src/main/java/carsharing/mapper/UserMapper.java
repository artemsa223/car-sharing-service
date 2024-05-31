package carsharing.mapper;

import carsharing.config.MapperConfig;
import carsharing.dto.auth.UserRegistrationRequestDto;
import carsharing.dto.auth.UserResponseDto;
import carsharing.exception.EntityNotFoundException;
import carsharing.model.Role;
import carsharing.model.User;
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
