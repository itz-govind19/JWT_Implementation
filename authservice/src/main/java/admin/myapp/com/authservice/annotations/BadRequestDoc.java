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
 * Custom annotation to document a 400 Bad Request API response in Swagger/OpenAPI.
 * <p>
 * This is typically used on controller methods that validate request bodies or parameters,
 * and is triggered when input is invalid, such as:
 * <ul>
 *   <li>Missing required fields</li>
 *   <li>Invalid field formats</li>
 *   <li>Malformed JSON</li>
 * </ul>
 * </p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * @BadRequestDoc
 * @PostMapping("/register")
 * public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterRequest request) {
 *     ...
 * }
 * }</pre>
 *
 * <p>The Swagger documentation will show a 400 response example like:</p>
 * <pre>
 * {
 *   "timestamp": "2025-07-06T02:10:00.000Z",
 *   "status": 400,
 *   "error": "Bad Request",
 *   "message": "Missing required field: 'email'",
 *   "path": "/api/v1/users/register"
 * }
 * </pre>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request format or missing fields",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                name = "Bad Request Example",
                                value = """
                {
                  "timestamp": "2025-07-06T02:10:00.000Z",
                  "status": 400,
                  "error": "Bad Request",
                  "message": "Missing required field: 'email'",
                  "path": "/api/v1/users/register"
                }
                """
                        )
                )
        )
})
public @interface BadRequestDoc {
}
