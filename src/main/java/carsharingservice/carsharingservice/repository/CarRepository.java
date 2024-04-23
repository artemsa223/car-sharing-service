package carsharingservice.carsharingservice.repository;

import carsharingservice.carsharingservice.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
