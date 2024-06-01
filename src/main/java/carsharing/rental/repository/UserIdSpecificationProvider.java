package carsharing.rental.repository;

import carsharing.rental.model.Rental;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserIdSpecificationProvider {
    public Specification<Rental> getSpecification(Long[] userIds) {
        return (root, query, criteriaBuilder) ->
                root.get("user").get("id").in(Arrays.asList(userIds));
    }
}
