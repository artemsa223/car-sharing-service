package carsharingservice.carsharingservice.controllers;

import carsharingservice.carsharingservice.dto.payment.CancelPaymentDto;
import carsharingservice.carsharingservice.dto.payment.CreatePaymentSessionRequestDto;
import carsharingservice.carsharingservice.dto.payment.PaymentDto;
import carsharingservice.carsharingservice.model.User;
import carsharingservice.carsharingservice.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    @Operation(summary = "Create payment session")
    public PaymentDto createPaymentSession(
            @RequestBody CreatePaymentSessionRequestDto request) {
        return paymentService.createPaymentSession(request);
    }

    @GetMapping("/success")
    @Operation(summary = "Check successful payment")
    public PaymentDto checkSuccessfulPayment(@RequestParam Long rentalId) {
        return paymentService.checkSuccessfulPaymentByRentalId(rentalId);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_CUSTOMER')")
    @GetMapping
    public List<PaymentDto> getPayments(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority()
                .equals("ROLE_MANAGER"))) {
            return paymentService.getAllPayments();
        } else if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority()
                .equals("ROLE_CUSTOMER"))) {
            return paymentService.getAllPaymentsByUserId(user.getId());
        }
        throw new RuntimeException("User has no permissions");
    }

    @GetMapping("/cancel")
    @Operation(summary = "Pause payment")
    public CancelPaymentDto cancelPayment(@RequestParam Long rentalId) {
        return paymentService.cancelPayment(rentalId);
    }
}
