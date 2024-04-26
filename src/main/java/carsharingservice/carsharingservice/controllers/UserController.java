package carsharingservice.carsharingservice.controllers;

import carsharingservice.carsharingservice.dto.auth.UserResponseDto;
import carsharingservice.carsharingservice.dto.user.UpdateRole;
import carsharingservice.carsharingservice.dto.user.UserUpdateDto;
import carsharingservice.carsharingservice.models.User;
import carsharingservice.carsharingservice.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}/role")
    @Operation(summary = "Update user's role", description = "Updates user's role")
    UserResponseDto updateUserRole(
            @PathVariable Long id,@RequestBody UpdateRole requestDto) {
        return userService.updateUserRole(id, requestDto);
    }

    @GetMapping("/me")
    public UserResponseDto getUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.getUserDtoById(user.getId());
    }

    @PutMapping("/me")
    public UserResponseDto updateUserProfileInfo(Authentication authentication,
                                         @RequestBody UserUpdateDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return userService.updateUser(user.getId(), requestDto);
    }

}
