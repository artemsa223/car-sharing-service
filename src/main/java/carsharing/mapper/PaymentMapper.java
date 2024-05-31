package carsharing.mapper;

import carsharing.config.MapperConfig;
import carsharing.dto.payment.PaymentDto;
import carsharing.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface PaymentMapper {
    @Mapping(target = "rentalId", source = "payment.rental.id")
    PaymentDto toDto(Payment payment);
}
