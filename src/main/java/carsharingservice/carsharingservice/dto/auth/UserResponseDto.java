package carsharingservice.carsharingservice.dto.auth;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String repeatPassword;
}
