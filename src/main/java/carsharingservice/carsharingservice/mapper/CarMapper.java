package carsharingservice.carsharingservice.mapper;

import carsharingservice.carsharingservice.config.MapperConfig;
import carsharingservice.carsharingservice.dto.cars.CarDto;
import carsharingservice.carsharingservice.dto.cars.CarNotDetailedDto;
import carsharingservice.carsharingservice.dto.cars.CreateCarRequestDto;
import carsharingservice.carsharingservice.models.Car;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CarMapper {
    CarDto toDto(Car car);

    Car toModel(CreateCarRequestDto carDto);

    CarNotDetailedDto toNotDetailedDto(Car car);
}
