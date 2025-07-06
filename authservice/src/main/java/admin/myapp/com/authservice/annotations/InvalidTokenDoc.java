package admin.myapp.com.authservice.annotations;

import admin.myapp.com.authservice.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to document a 400 Bad Request response for token-related issues in Swagger/OpenAPI.
 * <p>
 * This annotation is typically used on endpoints that validate JWTs or access tokens,
 * and should be applied when the token is:
 * <ul>
 *   <li>Expired</li>
 *   <li>Malformed</li>
 *   <li>Invalid or tampered</li>
 * </ul>
 * </p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * @InvalidTokenDoc
 * @GetMapping("/validate-token")
 * public ResponseEntity<?> validateToken(@RequestParam String token) {
 *     ...
 * }
 * }</pre>
 *
 * <p><b>Example 400 Token Error Response:</b></p>
 * <pre>
 * {
 *   "timestamp": "2025-07-06T02:20:00.000Z",
 *   "status": 400,
 *   "error": "Bad Request",
 *   "message": "Token is invalid or has expired",
 *   "path": "/api/v1/auth/validate-token"
 * }
 * </pre>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                description = "Token is invalid or expired",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                name = "Invalid Token Example",
                                value = """
                {
                  "timestamp": "2025-07-06T02:20:00.000Z",
                  "status": 400,
                  "error": "Bad Request",
                  "message": "Token is invalid or has expired",
                  "path": "/api/v1/auth/validate-token"
                }
                """
                        )
                )
        )
})
public @interface InvalidTokenDoc {
}
