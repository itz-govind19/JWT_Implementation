package admin.myapp.com.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a response containing a JWT token for authentication.
 */
@AllArgsConstructor
@Getter
public class AuthResponse {
    private final String token;
}
