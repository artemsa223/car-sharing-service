package carsharingservice.carsharingservice.repository.car;

import carsharingservice.carsharingservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
