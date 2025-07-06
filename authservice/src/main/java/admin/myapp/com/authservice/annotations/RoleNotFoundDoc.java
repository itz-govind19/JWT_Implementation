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
 * Custom annotation to document 404 Not Found responses related to roles.
 *
 * <p>This annotation is used to describe API responses where a requested role
 * does not exist or cannot be found in the system.</p>
 *
 * <p><b>Use Cases:</b></p>
 * <ul>
 *   <li>When querying roles by name or ID and the role does not exist</li>
 *   <li>Role-based authorization checks where the role is missing</li>
 * </ul>
 *
 * <p><b>Example 404 Response:</b></p>
 * <pre>
 * {
 *   "timestamp": "2025-07-06T02:00:00.000Z",
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "Specified role not found in the system",
 *   "path": "/api/v1/auth"
 * }
 * </pre>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "404",
                description = "Role Not Found - The specified role does not exist",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                name = "Role Not Found Response",
                                value = """
                {
                  "timestamp": "2025-07-06T02:00:00.000Z",
                  "status": 404,
                  "error": "Not Found",
                  "message": "Specified role not found in the system",
                  "path": "/api/v1/auth"
                }
                """
                        )
                )
        )
})
public @interface RoleNotFoundDoc {
}
