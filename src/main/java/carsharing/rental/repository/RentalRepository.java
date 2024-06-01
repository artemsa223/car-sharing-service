package carsharing.rental.repository;

import carsharing.rental.model.Rental;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RentalRepository extends JpaRepository<Rental, Long>,
        JpaSpecificationExecutor<Rental> {
    Optional<Rental> findById(Long rentalId);

    List<Rental> findAllByUserId(Long userId);
}
