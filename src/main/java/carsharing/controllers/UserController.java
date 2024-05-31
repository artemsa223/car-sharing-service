package carsharing.controllers;

import carsharing.dto.auth.UserResponseDto;
import carsharing.dto.user.UpdateRole;
import carsharing.dto.user.UserUpdateDto;
import carsharing.model.User;
import carsharing.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "users", description = "Operations with users")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/role")
    @Operation(summary = "Update user's role", description = "Updates user's role")
    UserResponseDto updateUserRole(
            @PathVariable Long id,@RequestBody UpdateRole requestDto) {
        return userService.updateUserRole(id, requestDto);
    }

    @GetMapping("/me")
    @Operation(summary = "Get user's profile info", description = "Gets user's profile info")
    public UserResponseDto getUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.getUserDtoById(user.getId());
    }

    @PutMapping("/me")
    @Operation(summary = "Update user's profile info", description = "Updates user's profile info")
    public UserResponseDto updateUserProfileInfo(Authentication authentication,
                                         @RequestBody UserUpdateDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return userService.updateUser(user.getId(), requestDto);
    }

}
