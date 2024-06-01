package carsharing.rental.repository;

import carsharing.rental.dto.RentalSearchParameters;
import carsharing.rental.model.Rental;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RentalSpecificationBuilder implements SpecificationBuilder<Rental> {
    private final UserIdSpecificationProvider userIdSpecificationProvider;
    private final IsActiveSpecificationProvider isActiveSpecificationProvider;

    @Override
    public Specification<Rental> build(RentalSearchParameters searchParameters) {
        Specification<Rental> spec = Specification.where(null);
        if (searchParameters.user_id() != null && searchParameters.user_id().length > 0) {
            spec = spec.and(userIdSpecificationProvider
                    .getSpecification(searchParameters.user_id()));
        }
        if (searchParameters.is_active() != null) {
            spec = spec.and(isActiveSpecificationProvider
                    .getSpecification(searchParameters.is_active()));
        }
        return spec;
    }
}
