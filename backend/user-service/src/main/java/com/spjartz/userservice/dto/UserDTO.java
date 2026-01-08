package com.spjartz.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "User Data Transfer Object")
public class UserDTO {

    @Schema(description = "User ID (ignored for create operations)", example = "1")
    private Long id;

    @Schema(description = "Username of the user", example = "johndoe")
    @NotNull
    private String username;

    @Schema(description = "Email of the user", example = "john.doe@example.com")
    @NotNull
    @Email
    private String email;

    @Schema(description = "First name of the user", example = "John")
    @NotNull
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    @NotNull
    private String lastName;
}