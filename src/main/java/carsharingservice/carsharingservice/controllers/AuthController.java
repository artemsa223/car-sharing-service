package carsharingservice.carsharingservice.controllers;

import carsharingservice.carsharingservice.dto.auth.UserLoginRequestDto;
import carsharingservice.carsharingservice.dto.auth.UserLoginResponseDto;
import carsharingservice.carsharingservice.dto.auth.UserRegistrationRequestDto;
import carsharingservice.carsharingservice.dto.auth.UserResponseDto;
import carsharingservice.carsharingservice.exception.RegistrationException;
import carsharingservice.carsharingservice.security.AuthenticationService;
import carsharingservice.carsharingservice.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "auth", description = "Operations with users")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);

    }

    @Operation(summary = "Register user", description = "Register new user")
    @PostMapping("/register")
    public UserResponseDto registerUser(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.registerUser(requestDto);
    }

    @Operation(summary = "Delete user", description = "Delete user by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

}
