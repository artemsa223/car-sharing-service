package carsharing.payment.repository;

import carsharing.payment.model.Payment;
import carsharing.rental.model.Rental;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDao {

    @PersistenceContext
    private EntityManager em;

    public List<Payment> findAllByRentalsId(List<Long> rentalIds) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Payment> cq = cb.createQuery(Payment.class);
        Root<Payment> paymentRoot = cq.from(Payment.class);

        // Join with rental entity
        Join<Payment, Rental> rentalJoin = paymentRoot.join("rental", JoinType.LEFT);

        // Create predicates
        Predicate rentalIdPredicate = rentalJoin.get("id").in(rentalIds);

        // Apply the WHERE clause
        cq.where(rentalIdPredicate);

        TypedQuery<Payment> query = em.createQuery(cq);
        return query.getResultList();
    }
}
