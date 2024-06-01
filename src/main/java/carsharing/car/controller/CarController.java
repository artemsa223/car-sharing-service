package carsharing.car.controller;

import carsharing.car.dto.CarDto;
import carsharing.car.dto.CarNotDetailedDto;
import carsharing.car.dto.CreateCarRequestDto;
import carsharing.car.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/cars")
@Tag(name = "cars", description = "Operations with cars")
public class CarController {
    private final CarService carService;

    @Operation(summary = "Find all cars", description = "Retrieve a list of cars")
    @GetMapping
    public List<CarNotDetailedDto> findNotDetailedAll(Pageable pageable) {
        return carService.findNotDetailedAll(pageable);
    }

    @Operation(summary = "Find all cars", description = "Retrieve a list of cars")
    @GetMapping("/")
    public List<CarDto> findAll(Pageable pageable) {
        return carService.findAll(pageable);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "add car",
            description = "Return car if course is added")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CarDto addCar(@RequestBody CreateCarRequestDto requestDto) {
        return carService.createCar(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Delete a car by id", description = "Delete a car by its id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        carService.deleteById(id);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Update a car by id", description = "Update a car by its id")
    @PutMapping("/{id}")
    public CarDto updateCar(@PathVariable Long id,
                              @RequestBody @Valid CreateCarRequestDto requestDto) {
        return carService.updateById(requestDto, id);
    }

}
