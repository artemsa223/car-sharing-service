package carsharing.service.rental;

import carsharing.dto.rental.CreateRentalRequestDto;
import carsharing.dto.rental.RentalResponseDto;
import carsharing.dto.rental.RentalSearchParameters;
import carsharing.dto.rental.SetActualReturnDateRequestDto;
import java.util.List;

public interface RentalService {
    RentalResponseDto addRental(Long userId, CreateRentalRequestDto request);

    List<RentalResponseDto> getCustomerRentals(Long userId);

    List<RentalResponseDto> getSpecificIdRentals(Long rentalId);

    void setActualReturnDate(Long rentalId, SetActualReturnDateRequestDto request);

    List<RentalResponseDto> searchRentals(RentalSearchParameters searchParameters);

}
