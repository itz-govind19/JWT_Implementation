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
 * Custom annotation to document a 404 Not Found response when no users are found by a specified role.
 * <p>
 * This annotation is typically applied to endpoints that retrieve users based on roles (e.g., ADMIN, USER),
 * and should be used when no matching users exist in the system.
 * </p>
 *
 * <p><b>When to Use:</b></p>
 * <ul>
 *     <li>Fetching users by role returns an empty or null result</li>
 *     <li>You want to standardize 404 Swagger documentation for such cases</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * @NoUserFoundByRoleDoc
 * @GetMapping("/get-by-role")
 * public ResponseEntity<List<User>> getUsersByRole(@RequestParam String role) {
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
 *   "message": "No user found with the specified role",
 *   "path": "/api/v1/admin/get/role"
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
                                name = "User Not Found by Role",
                                value = """
                {
                  "timestamp": "2025-07-06T01:42:55.249208600Z",
                  "status": 404,
                  "error": "Not Found",
                  "message": "No user found with the specified role",
                  "path": "/api/v1/admin/get/role"
                }
                """
                        )
                )
        )
})
public @interface NoUserFoundByRoleDoc {
}
