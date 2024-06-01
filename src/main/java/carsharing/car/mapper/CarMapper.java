package carsharing.car.mapper;

import carsharing.auth.config.MapperConfig;
import carsharing.car.dto.CarDto;
import carsharing.car.dto.CarNotDetailedDto;
import carsharing.car.dto.CreateCarRequestDto;
import carsharing.car.model.Car;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CarMapper {
    CarDto toDto(Car car);

    Car toModel(CreateCarRequestDto carDto);

    Car toModel(CarDto carDto);

    CarNotDetailedDto toNotDetailedDto(Car car);
}
