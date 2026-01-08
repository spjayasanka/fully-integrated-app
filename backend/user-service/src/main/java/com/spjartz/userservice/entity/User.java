package com.spjartz.userservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User entity")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Username of the user", example = "johndoe")
    private String username;

    @Column(nullable = false, unique = true)
    @Schema(description = "Email of the user", example = "john.doe@example.com")
    private String email;

    @Column(nullable = false)
    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @Column(nullable = false)
    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;
}