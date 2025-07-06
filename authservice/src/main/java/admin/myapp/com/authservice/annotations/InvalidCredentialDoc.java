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
 * Custom annotation to document a 401 Unauthorized response in Swagger/OpenAPI.
 * <p>
 * This is typically used on authentication endpoints, such as login,
 * where invalid credentials are provided by the user.
 * </p>
 *
 * <p><b>When to Use:</b></p>
 * <ul>
 *     <li>User provides wrong username or password</li>
 *     <li>Token-based authentication fails due to expired or tampered tokens</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * @InvalidCredentialDoc
 * @PostMapping("/login")
 * public ResponseEntity<?> login(@RequestBody LoginRequest request) {
 *     ...
 * }
 * }</pre>
 *
 * <p><b>Example 401 Response:</b></p>
 * <pre>
 * {
 *   "timestamp": "2025-07-06T01:42:55.249208600Z",
 *   "status": 401,
 *   "error": "UNAUTHORIZED",
 *   "message": "Invalid Credential",
 *   "path": "/api/v1/auth/endpoint"
 * }
 * </pre>
 */
@Target({ElementType.METHOD, ElementType.TYPE})  // applicable to methods and classes
@Retention(RetentionPolicy.RUNTIME)  // available at runtime for reflection
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "401",
                description = "Invalid Credential",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                name = "Forbidden Response",
                                value = """
                                        {
                                          "timestamp": "2025-07-06T01:42:55.249208600Z",
                                          "status": 401,
                                          "error": "UNAUTHORIZED",
                                          "message": "Invalid Credential",
                                          "path": "/api/v1/auth/endpoint"
                                        }
                                        """
                        )
                )
        )
})
public @interface InvalidCredentialDoc {
}