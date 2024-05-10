package carsharingservice.carsharingservice.service.rental;

import carsharingservice.carsharingservice.dto.rental.CreateRentalRequestDto;
import carsharingservice.carsharingservice.dto.rental.RentalResponseDto;
import carsharingservice.carsharingservice.dto.rental.RentalSearchParameters;
import carsharingservice.carsharingservice.dto.rental.SetActualReturnDateRequestDto;
import java.util.List;

public interface RentalService {
    RentalResponseDto addRental(Long userId, CreateRentalRequestDto request);

    List<RentalResponseDto> getCustomerRentals(Long userId);

    List<RentalResponseDto> getSpecificIdRentals(Long rentalId);

    void setActualReturnDate(Long rentalId, SetActualReturnDateRequestDto request);

    List<RentalResponseDto> searchRentals(RentalSearchParameters searchParameters);

}
