package carsharing.service.car;

import carsharing.dto.cars.CarDto;
import carsharing.dto.cars.CarNotDetailedDto;
import carsharing.dto.cars.CreateCarRequestDto;
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
