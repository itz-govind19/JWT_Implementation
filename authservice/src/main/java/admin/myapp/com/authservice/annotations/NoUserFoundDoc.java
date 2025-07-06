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
 * Custom annotation to document a 404 Not Found response when no user is found in the system.
 * <p>
 * This annotation should be used in generic cases where a user is expected to be found
 * (by email, username, current session, etc.) but is missing or not retrievable.
 * </p>
 *
 * <p><b>When to Use:</b></p>
 * <ul>
 *     <li>Fetching the current logged-in user's profile fails</li>
 *     <li>Looking up a user without specific filtering like ID or role</li>
 *     <li>Generic user retrieval from session, token, or username</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * @NoUserFoundDoc
 * @GetMapping("/getDetails")
 * public ResponseEntity<User> getCurrentUserDetails(Authentication authentication) {
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
 *   "message": "No user found",
 *   "path": "/api/v1/admin/endpoint"
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
                                name = "Specified User Not Found",
                                value = """
                {
                  "timestamp": "2025-07-06T01:42:55.249208600Z",
                  "status": 404,
                  "error": "Not Found",
                  "message": "No user found",
                  "path": "/api/v1/admin/endpoint"
                }
                """
                        )
                )
        )
})
public @interface NoUserFoundDoc {
}
