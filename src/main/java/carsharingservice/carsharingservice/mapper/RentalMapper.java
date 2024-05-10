package carsharingservice.carsharingservice.mapper;

import carsharingservice.carsharingservice.config.MapperConfig;
import carsharingservice.carsharingservice.dto.rental.CreateRentalRequestDto;
import carsharingservice.carsharingservice.dto.rental.RentalResponseDto;
import carsharingservice.carsharingservice.model.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapperConfig.class)
public interface RentalMapper {
    @Mappings({
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "carId", source = "car.id"),
            @Mapping(target = "rentalId", source = "rental.id")
    })
    RentalResponseDto toDto(Rental rental);

    @Mapping(target = "car", ignore = true)
    Rental toModel(CreateRentalRequestDto rentalDto);
}
