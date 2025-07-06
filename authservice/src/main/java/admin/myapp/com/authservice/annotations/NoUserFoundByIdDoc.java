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
 * Custom annotation to document a 404 Not Found response when a user is not found by ID.
 * <p>
 * Typically used in endpoints that retrieve a user by a unique identifier (e.g., user ID),
 * and should be applied when no record exists for the provided ID.
 * </p>
 *
 * <p><b>When to Use:</b></p>
 * <ul>
 *     <li>When querying a user by ID and the result is null or not found</li>
 *     <li>To standardize Swagger/OpenAPI documentation for such 404 responses</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * @NoUserFoundByIdDoc
 * @GetMapping("/get")
 * public ResponseEntity<User> getUserById(@RequestParam Long id) {
 *     ...
 * }
 * }</pre>
 *
 * <p><b>Example 404 Response:</b></p>
 * <pre>
 * {
 *   "timestamp": "2025-07-06T01:42:55.249208600Z",
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "No user found with the specified ID",
 *   "path": "/api/v1/admin/get/1"
 * }
 * </pre>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "404",
                description = "User Not Found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                name = "User Not Found by ID",
                                value = """
                {
                  "timestamp": "2025-07-06T01:42:55.249208600Z",
                  "status": 404,
                  "error": "Not Found",
                  "message": "No user found with the specified ID",
                  "path": "/api/v1/admin/get/1"
                }
                """
                        )
                )
        )
})
public @interface NoUserFoundByIdDoc {
}


