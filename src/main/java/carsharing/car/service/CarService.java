package carsharing.car.service;

import carsharing.car.dto.CarDto;
import carsharing.car.dto.CarNotDetailedDto;
import carsharing.car.dto.CreateCarRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CarService {

    List<CarDto> findAll(Pageable pageable);

    List<CarNotDetailedDto> findNotDetailedAll(Pageable pageable);

    CarDto getById(Long id);

    CarDto createCar(CreateCarRequestDto requestDto);

    void deleteById(Long id);

    CarDto updateById(CreateCarRequestDto requestDto, Long id);

}
