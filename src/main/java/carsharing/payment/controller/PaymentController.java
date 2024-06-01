package carsharing.payment.controller;

import carsharing.payment.dto.CancelPaymentDto;
import carsharing.payment.dto.CreatePaymentSessionRequestDto;
import carsharing.payment.dto.PaymentDto;
import carsharing.user.model.User;
import carsharing.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "payments", description = "Operations with payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    @Operation(summary = "Create payment session",
            description = "Create payment session for rental")
    public PaymentDto createPaymentSession(
            @RequestBody CreatePaymentSessionRequestDto request) {
        return paymentService.createPaymentSession(request);
    }

    @GetMapping("/success")
    @Operation(summary = "Check successful payment",
            description = "Check successful payment by rental id")
    public PaymentDto checkSuccessfulPayment(@RequestParam Long rentalId) {
        return paymentService.checkSuccessfulPaymentByRentalId(rentalId);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_CUSTOMER')")
    @GetMapping
    @Operation(summary = "Get payments",
            description = "Get all payments for manager. And for customer only his payments")
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
    @Operation(summary = "Pause payment",
            description = "Pause payment by rental id for 24 hours")
    public CancelPaymentDto cancelPayment(@RequestParam Long rentalId) {
        return paymentService.cancelPayment(rentalId);
    }
}
