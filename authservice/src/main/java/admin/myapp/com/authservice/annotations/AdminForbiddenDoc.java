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
 * Custom annotation to document a 403 Forbidden API response in Swagger/OpenAPI.
 * <p>
 * This annotation is intended to be used on controller methods or classes
 * that are restricted to specific roles (e.g., ADMIN).
 * It provides a standard error response documentation for cases where
 * a user does not have the required permissions.
 * </p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * @AdminForbiddenDoc
 * @GetMapping("/admin-only")
 * public ResponseEntity<?> restrictedEndpoint() {
 *     ...
 * }
 * }</pre>
 *
 * The generated Swagger documentation will include a 403 response with an example:
 * <pre>
 * {
 *   "timestamp": "2025-07-06T01:42:55.249208600Z",
 *   "status": 403,
 *   "error": "Forbidden",
 *   "message": "Unauthorized: Access Denied",
 *   "path": "/api/v1/admin/endpoint"
 * }
 * </pre>
 */
@Target({ElementType.METHOD, ElementType.TYPE})  // applicable to methods and classes
@Retention(RetentionPolicy.RUNTIME)  // available at runtime for reflection
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "403",
                description = "Forbidden - User does not have required role",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                name = "Forbidden Response",
                                value = """
                {
                  "timestamp": "2025-07-06T01:42:55.249208600Z",
                  "status": 403,
                  "error": "Forbidden",
                  "message": "Unauthorized: Access Denied",
                  "path": "/api/v1/admin/endpoint"
                }
                """
                        )
                )
        )
})
public @interface AdminForbiddenDoc {
}