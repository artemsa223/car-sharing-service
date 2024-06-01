package carsharing.payment.mapper;

import carsharing.auth.config.MapperConfig;
import carsharing.payment.dto.PaymentDto;
import carsharing.payment.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface PaymentMapper {
    @Mapping(target = "rentalId", source = "payment.rental.id")
    PaymentDto toDto(Payment payment);
}
