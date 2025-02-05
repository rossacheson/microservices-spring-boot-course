package com.rossacheson.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name="Customer",
        description="Schema to hold Customer and Account information"
)
public class CustomerDto {
    @Schema(description = "Name of the customer", example="John Doe")
    @NotEmpty(message = "Name cannot be empty")
    @Size(min=2, max=50, message = "Name must be between 2 and 50 characters")
    private String name;
    @Schema(description = "Email of the customer", example="john@test.com")
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
    @Schema(description = "Mobile number of the customer", example="9345432123")
    @Pattern(regexp = "^$|[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;
    @Schema(description = "Account details of the customer")
    private AccountDto accountDto;
}
