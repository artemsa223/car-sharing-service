package carsharing.service.car;

import carsharing.dto.cars.CarDto;
import carsharing.dto.cars.CarNotDetailedDto;
import carsharing.dto.cars.CreateCarRequestDto;
import carsharing.exception.EntityNotFoundException;
import carsharing.mapper.CarMapper;
import carsharing.model.Car;
import carsharing.repository.car.CarRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public List<CarDto> findAll(Pageable pageable) {
        return carRepository.findAll(pageable).stream()
                .map(carMapper::toDto)
                .toList();
    }

    @Override
    public List<CarNotDetailedDto> findNotDetailedAll(Pageable pageable) {
        return carRepository.findAll(pageable).stream()
                .map(carMapper::toNotDetailedDto)
                .toList();
    }

    @Override
    public CarDto getById(Long id) {
        return carMapper.toDto(carRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Car not found by id " + id)));
    }

    @Override
    public CarDto createCar(CreateCarRequestDto requestDto) {
        return carMapper.toDto(carRepository.save(carMapper.toModel(requestDto)));
    }

    @Override
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public CarDto updateById(CreateCarRequestDto requestDto, Long id) {
        if (!carRepository.existsById(id)) {
            throw new EntityNotFoundException("Car not found by id " + id);
        }
        Car update = carMapper.toModel(requestDto);
        update.setId(id);
        return carMapper.toDto(carRepository.save(update));
    }
}
