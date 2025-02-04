package com.rossacheson.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {
    @NotEmpty(message = "Name cannot be empty")
    @Size(min=2, max=50, message = "Name must be between 2 and 50 characters")
    private String name;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
    @Pattern(regexp = "^$|[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;
    private AccountDto accountDto;
}
