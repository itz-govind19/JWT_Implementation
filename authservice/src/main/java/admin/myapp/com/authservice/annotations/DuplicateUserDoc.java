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
 * Custom annotation to document a 409 Conflict response in Swagger/OpenAPI.
 * <p>
 * This is typically used on controller methods that handle user registration or updates,
 * where a conflict may occur due to:
 * <ul>
 *   <li>Existing username</li>
 *   <li>Duplicate email</li>
 *   <li>Resource already present in the system</li>
 * </ul>
 * </p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * @DuplicateUserDoc
 * @PostMapping("/register")
 * public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
 *     ...
 * }
 * }</pre>
 *
 * <p>The Swagger documentation will show a 409 response like the following:</p>
 * <pre>
 * {
 *   "timestamp": "2025-07-06T01:42:55.249208600Z",
 *   "status": 409,
 *   "error": "Conflict",
 *   "message": "Username already exists",
 *   "path": "/api/v1/auth/register"
 * }
 * </pre>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "409",
                description = "Conflict - Username already exists",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                name = "Duplicate User Response",
                                value = """
                                {
                                  "timestamp": "2025-07-06T01:42:55.249208600Z",
                                  "status": 409,
                                  "error": "Conflict",
                                  "message": "Username already exists",
                                  "path": "/api/v1/auth/register"
                                }
                                """
                        )
                )
        )
})
public @interface DuplicateUserDoc {
}
