package com.rossacheson.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {
    @Schema(description = "API path invoked by the client")
    private String apiPath;
    @Schema(description = "Error code returned by the server")
    private HttpStatus errorCode;
    @Schema(description = "Error message returned by the server")
    private String errorMessage;
    @Schema(description = "Time when the error occurred")
    private LocalDateTime errorTime;
}
