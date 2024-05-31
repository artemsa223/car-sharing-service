package carsharing.service.rental;

import carsharing.dto.cars.CarDto;
import carsharing.dto.rental.CreateRentalRequestDto;
import carsharing.dto.rental.RentalResponseDto;
import carsharing.dto.rental.RentalSearchParameters;
import carsharing.dto.rental.SetActualReturnDateRequestDto;
import carsharing.exception.EntityNotFoundException;
import carsharing.mapper.CarMapper;
import carsharing.mapper.RentalMapper;
import carsharing.model.Car;
import carsharing.model.Rental;
import carsharing.model.User;
import carsharing.repository.car.CarRepository;
import carsharing.repository.rental.RentalRepository;
import carsharing.repository.rental.RentalSpecificationBuilder;
import carsharing.service.car.CarService;
import carsharing.service.notification.NotificationService;
import carsharing.service.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private static final String NEW_RENTAL_BOOKED_MESSAGE = "New rental booked:";

    private final RentalMapper rentalMapper;
    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final CarService carService;
    private final CarMapper carMapper;
    private final CarRepository carRepository;
    private final RentalSpecificationBuilder rentalSpecificationBuilder;
    private final NotificationService notificationService;

    @Transactional
    @Override
    public RentalResponseDto addRental(Long userId, CreateRentalRequestDto request) {
        Rental rental = rentalMapper.toModel(request);
        User user = userService.getById(userId);
        rental.setUser(user);
        CarDto carDto = carService.getById(request.carId());
        Car car = carMapper.toModel(carDto);
        if (car.getInventoryNumber() < 1) {
            throw new RuntimeException("Car is not available");
        }
        car.setInventoryNumber(car.getInventoryNumber() - 1);
        rental.setCar(car);
        carMapper.toDto(carRepository.save(car));
        Rental savedRental = rentalRepository.save(rental);
        RentalResponseDto response = rentalMapper.toDto(savedRental);
        response.setRentalId(savedRental.getId());

        String notificationMessage = buildNotificationMessage(rental);
        notificationService.sendNotification(NEW_RENTAL_BOOKED_MESSAGE
                + notificationMessage);

        return response;
    }

    @Override
    public List<RentalResponseDto> getCustomerRentals(Long userId) {
        List<Rental> rentals = rentalRepository.findAllByUserId(userId);
        return rentals.stream()
                .map(rentalMapper::toDto)
                .toList();
    }

    @Override
    public List<RentalResponseDto> getSpecificIdRentals(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(() ->
                new EntityNotFoundException("Can't find rental with id: " + rentalId));
        return List.of(rentalMapper.toDto(rental));
    }

    @Override
    public void setActualReturnDate(Long rentalId, SetActualReturnDateRequestDto request) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
        rental.setActualReturnDate(request.actualReturnDate());
        rentalRepository.save(rental);
        Car car = rental.getCar();
        car.setInventoryNumber(car.getInventoryNumber() + 1);
        carRepository.save(car);
        rentalRepository.save(rental);
    }

    @Override
    public List<RentalResponseDto> searchRentals(RentalSearchParameters searchParameters) {
        Specification<Rental> rentalSpecification =
                rentalSpecificationBuilder.build(searchParameters);
        return rentalRepository.findAll(rentalSpecification).stream()
                .map(rentalMapper::toDto)
                .toList();
    }

    private String buildNotificationMessage(Rental rental) {
        return " Rental ID: " + rental.getId() + System.lineSeparator()
                + " Car: " + rental.getCar().getModel() + System.lineSeparator()
                + " User: " + rental.getUser().getFirstName() + System.lineSeparator()
                + " Start date: " + rental.getRentalDate() + System.lineSeparator()
                + " End date: " + rental.getReturnDate() + System.lineSeparator();
    }
}