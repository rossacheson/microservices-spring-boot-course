package com.rossacheson.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name="Account",
        description="Schema to hold Account information"
)
public class AccountDto {
    @NotEmpty(message = "Account number cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
    @Schema(description = "Account number of Eazy Bank account", example="1234567890")
    private Long accountNumber;

    @NotEmpty(message = "Account type cannot be empty")
    @Schema(description = "Account type of Eazy Bank account", example="Savings")
    private String accountType;
    @NotEmpty(message = "Branch address cannot be empty")
    @Schema(description = "Branch address of Eazy Bank account", example="123 Main Street, New York")
    private String branchAddress;
}
