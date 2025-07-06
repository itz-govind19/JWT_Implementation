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
 * Annotation to document 401 Unauthorized responses.
 *
 * <p>This annotation is intended for endpoints where the JWT token is either missing,
 * invalid, or expired, resulting in an unauthorized access attempt.</p>
 *
 * <p><b>Typical usage scenarios:</b></p>
 * <ul>
 *   <li>Missing JWT token in the request header</li>
 *   <li>Invalid or malformed JWT token</li>
 *   <li>Expired JWT token</li>
 * </ul>
 *
 * <p><b>Example 401 Response:</b></p>
 * <pre>
 * {
 *   "timestamp": "2025-07-06T01:42:55.249208600Z",
 *   "status": 401,
 *   "error": "Unauthorized",
 *   "message": "JWT token is missing or invalid",
 *   "path": "/api/v1/endpoint"
 * }
 * </pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "401",
                description = "Unauthorized - Invalid or missing JWT token",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                name = "Unauthorized Response",
                                value = """
                {
                    "timestamp": "2025-07-06T01:42:55.249208600Z",
                    "status": 401,
                    "error": "Unauthorized",
                    "message": "JWT token is missing or invalid",
                    "path": "/api/v1/endpoint"
                }
                """
                        )
                )
        )
})
public @interface UnauthorizedDoc {
}