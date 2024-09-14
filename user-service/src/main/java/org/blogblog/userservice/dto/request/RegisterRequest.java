package org.blogblog.userservice.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.blogblog.userservice.validators.RegexDefinition;

@Data
public class RegisterRequest {
    @Column(nullable = false)
    @NotNull(message = "First name cannot be null")
    private String firstName;

    @Column(nullable = false)
    @NotNull(message = "Last name cannot be null")
    private String lastName;

    @Column(unique = true, length = 100, nullable = false)
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
