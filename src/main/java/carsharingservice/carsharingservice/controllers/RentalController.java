package carsharingservice.carsharingservice.controllers;

import carsharingservice.carsharingservice.dto.rental.CreateRentalRequestDto;
import carsharingservice.carsharingservice.dto.rental.RentalResponseDto;
import carsharingservice.carsharingservice.dto.rental.RentalSearchParameters;
import carsharingservice.carsharingservice.dto.rental.SetActualReturnDateRequestDto;
import carsharingservice.carsharingservice.exception.BadRequestException;
import carsharingservice.carsharingservice.model.User;
import carsharingservice.carsharingservice.service.rental.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/rentals")
@RestController
public class RentalController {
    private final RentalService rentalService;
    private final UserDetailsService userDetailsService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping
    @Operation(summary = "Add a new rental", description = "Adds a new rental")
    public RentalResponseDto addRental(Authentication authentication,
                                       @RequestBody CreateRentalRequestDto request) {
        User user = (User) authentication.getPrincipal();
        return rentalService.addRental(user.getId(), request);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/return/{id}")
    @Operation(summary = "Set actual return date")
    public void setActualReturnDate(Authentication authentication,
                                    @RequestBody SetActualReturnDateRequestDto request,
                                    @PathVariable Long id) {
        rentalService.setActualReturnDate(id, request);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/search/")
    @Operation(summary = "Get active rentals",
            description = "Gets rentals by user ID and whether the rental is still active or not")
    public List<RentalResponseDto> getActiveRentals(RentalSearchParameters searchParameters) {
        return rentalService.searchRentals(searchParameters);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_CUSTOMER')")
    @GetMapping(value = {"/{id}", "/"})
    @Operation(summary = "Get rental", description = "Gets rentals")
    public List<RentalResponseDto> getRentals(Authentication authentication,
                                              @PathVariable(required = false) Long id) {
        User user = (User) authentication.getPrincipal();
        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority()
                .equals("ROLE_MANAGER")) && id != null) {
            return rentalService.getSpecificIdRentals(id);
        }
        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority()
                .equals("ROLE_CUSTOMER")) && id == null) {
            return rentalService.getCustomerRentals(user.getId());
        }
        throw new BadRequestException("Bad request");
    }

}
