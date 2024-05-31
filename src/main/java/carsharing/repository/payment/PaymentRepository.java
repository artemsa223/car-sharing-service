package carsharing.repository.payment;

import carsharing.model.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByRentalId(Long rentalId);

    @Query("FROM Payment p LEFT JOIN FETCH p.rental r "
            + "WHERE p.paymentStatus = 'PAID' AND r.id = :rentalId")
    Optional<Payment> findSuccessfulPaymentByRentalId(long rentalId);

}
