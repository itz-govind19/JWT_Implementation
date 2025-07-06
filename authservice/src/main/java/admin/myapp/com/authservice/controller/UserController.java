package admin.myapp.com.authservice.controller;

import admin.myapp.com.authservice.annotations.BadRequestDoc;
import admin.myapp.com.authservice.annotations.DuplicateUserDoc;
import admin.myapp.com.authservice.annotations.NoUserFoundDoc;
import admin.myapp.com.authservice.constant.Constants;
import admin.myapp.com.authservice.entity.User;
import admin.myapp.com.authservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for user-specific operations such as retrieving the current user,
 * updating user profile, and fetching user roles.
 */
@RestController
@RequestMapping(Constants.BASE_URL_USER)
@Tag(name = "User Controller", description = "Handles user-specific operations")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    /**
     * Retrieves details of the currently authenticated user.
     *
     * @return ResponseEntity containing the User object for the authenticated user
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Returns the currently authenticated user's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @NoUserFoundDoc
    public ResponseEntity<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(user);
    }

    /**
     * Updates the profile of the currently authenticated user with the given details.
     *
     * @param updatedUser User object containing updated information
     * @return ResponseEntity containing the updated User object
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/update")
    @Operation(summary = "Update current user", description = "Updates the currently authenticated user's profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @NoUserFoundDoc
    @DuplicateUserDoc
    @BadRequestDoc
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User updated = userService.updateUser(authentication.getName(), updatedUser);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves the list of role names assigned to the currently authenticated user.
     *
     * @return ResponseEntity containing a list of role names for the authenticated user
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/roles")
    @Operation(summary = "Get user roles", description = "Returns roles assigned to the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @NoUserFoundDoc
    public ResponseEntity<List<String>> getUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getCurrentUser(authentication.getName());
        List<String> roleNames = user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());
        return ResponseEntity.ok(roleNames);
    }
}
