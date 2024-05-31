package carsharing.repository.rental;

import carsharing.model.Rental;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsActiveSpecificationProvider {
    public Specification<Rental> getSpecification(boolean isActive) {
        return (root, query, criteriaBuilder) -> {
            if (isActive) {
                return criteriaBuilder.isNull(root.get("actualReturnDate"));
            }
            return criteriaBuilder.isNotNull(root.get("actualReturnDate"));
        };
    }
}
