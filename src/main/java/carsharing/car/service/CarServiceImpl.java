package carsharing.car.service;

import carsharing.car.dto.CarDto;
import carsharing.car.dto.CarNotDetailedDto;
import carsharing.car.dto.CreateCarRequestDto;
import carsharing.car.mapper.CarMapper;
import carsharing.car.model.Car;
import carsharing.car.repository.CarRepository;
import carsharing.exception.EntityNotFoundException;
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
