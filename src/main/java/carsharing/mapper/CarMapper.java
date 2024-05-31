package carsharing.mapper;

import carsharing.config.MapperConfig;
import carsharing.dto.cars.CarDto;
import carsharing.dto.cars.CarNotDetailedDto;
import carsharing.dto.cars.CreateCarRequestDto;
import carsharing.model.Car;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CarMapper {
    CarDto toDto(Car car);

    Car toModel(CreateCarRequestDto carDto);

    Car toModel(CarDto carDto);

    CarNotDetailedDto toNotDetailedDto(Car car);
}
