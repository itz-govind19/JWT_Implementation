package admin.myapp.com.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * A standard response wrapper for API responses, typically used for non-successful outcomes
 * such as errors or informational messages.
 */
@Data
@AllArgsConstructor
public class ApiResponse {

    /**
     * HTTP status code of the response (e.g., 200, 404, 500).
     */
    private int status;

    /**
     * Human-readable message describing the result of the operation.
     */
    private String message;

    /**
     * Timestamp indicating when the response was generated.
     */
    private LocalDateTime timestamp;
}
