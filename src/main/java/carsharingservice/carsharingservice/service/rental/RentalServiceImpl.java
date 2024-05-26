package carsharingservice.carsharingservice.service.rental;

import carsharingservice.carsharingservice.dto.cars.CarDto;
import carsharingservice.carsharingservice.dto.rental.CreateRentalRequestDto;
import carsharingservice.carsharingservice.dto.rental.RentalResponseDto;
import carsharingservice.carsharingservice.dto.rental.RentalSearchParameters;
import carsharingservice.carsharingservice.dto.rental.SetActualReturnDateRequestDto;
import carsharingservice.carsharingservice.exception.EntityNotFoundException;
import carsharingservice.carsharingservice.mapper.CarMapper;
import carsharingservice.carsharingservice.mapper.RentalMapper;
import carsharingservice.carsharingservice.model.Car;
import carsharingservice.carsharingservice.model.Rental;
import carsharingservice.carsharingservice.model.User;
import carsharingservice.carsharingservice.repository.car.CarRepository;
import carsharingservice.carsharingservice.repository.rental.RentalRepository;
import carsharingservice.carsharingservice.repository.rental.RentalSpecificationBuilder;
import carsharingservice.carsharingservice.service.car.CarService;
import carsharingservice.carsharingservice.service.notification.NotificationService;
import carsharingservice.carsharingservice.service.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private static final String NEW_RENTAL_BOOKED_MESSAGE = "New rental booked:";
    private static final String OVERDUE_RENTAL_MESSAGE = "Overdue rental:";
    private static final String NO_OVERDUE_MESSAGE = "No rentals overdue today!";

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
