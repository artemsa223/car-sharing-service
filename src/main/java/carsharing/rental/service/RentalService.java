package carsharing.rental.service;

import carsharing.rental.dto.CreateRentalRequestDto;
import carsharing.rental.dto.RentalResponseDto;
import carsharing.rental.dto.RentalSearchParameters;
import carsharing.rental.dto.SetActualReturnDateRequestDto;
import java.util.List;

public interface RentalService {
    RentalResponseDto addRental(Long userId, CreateRentalRequestDto request);

    List<RentalResponseDto> getCustomerRentals(Long userId);

    List<RentalResponseDto> getSpecificIdRentals(Long rentalId);

    void setActualReturnDate(Long rentalId, SetActualReturnDateRequestDto request);

    List<RentalResponseDto> searchRentals(RentalSearchParameters searchParameters);

}
