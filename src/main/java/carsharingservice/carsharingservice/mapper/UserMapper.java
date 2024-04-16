package carsharingservice.carsharingservice.mapper;

import carsharingservice.carsharingservice.config.MapperConfig;
import carsharingservice.carsharingservice.dto.UserRegistrationRequestDto;
import carsharingservice.carsharingservice.dto.UserResponseDto;
import carsharingservice.carsharingservice.models.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(config = MapperConfig.class)
@Component
public interface UserMapper {
    User toModel(UserRegistrationRequestDto registrationRequestDto);

    UserResponseDto toDto(User user);
}
