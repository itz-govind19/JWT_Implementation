package admin.myapp.com.authservice.controller;


import admin.myapp.com.authservice.annotations.AdminForbiddenDoc;
import admin.myapp.com.authservice.annotations.NoUserFoundByIdDoc;
import admin.myapp.com.authservice.annotations.NoUserFoundDoc;
import admin.myapp.com.authservice.constant.Constants;
import admin.myapp.com.authservice.entity.User;
import admin.myapp.com.authservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for admin-specific operations related to user management.
 * Only users with ADMIN role have access to these endpoints.
 */
@RestController
@RequestMapping(Constants.BASE_URL_ADMIN)
@Tag(name = "Admin Controller", description = "Admin-only operations for managing users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * Retrieves a list of all users with the ADMIN role.
     *
     * @return ResponseEntity containing a list of admin users
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get")
    @Operation(summary = "Get All Admin Users", description = "Returns a list of all users with ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of admin users retrieved successfully"),
    })
    @AdminForbiddenDoc
    public ResponseEntity<List<User>> getAllAdmins() {
        return ResponseEntity.ok(userService.getAllAdmins());
    }

    /**
     * Retrieves user details by the specified user ID.
     *
     * @param id The ID of the user to retrieve
     * @return ResponseEntity containing the user details
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/")
    @Operation(summary = "Get User by ID", description = "Returns any user's details by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved"),
    })
    @AdminForbiddenDoc // 403 Forbidden if access denied
    @NoUserFoundByIdDoc // 404 Not Found if user does not exist
    public ResponseEntity<User> getUserById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Retrieves all users in the system.
     *
     * @return ResponseEntity containing a list of all users
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUser")
    @Operation(summary = "Get All Users", description = "Returns all users in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved"),
    })
    @AdminForbiddenDoc
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    /**
     * Retrieves details of the currently authenticated admin user.
     *
     * @param authentication Authentication object representing the current user
     * @return ResponseEntity containing the current admin user's details
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getDetails")
    @Operation(summary = "Get Current Admin Details", description = "Returns details of the authenticated admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Current user details retrieved"),
    })
    @AdminForbiddenDoc
    @NoUserFoundDoc
    public ResponseEntity<User> getAdminDetails(Authentication authentication) {
        return ResponseEntity.ok(userService.getCurrentUser(authentication.getName()));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete
     * @return ResponseEntity confirming successful deletion
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/delete/")
    @Operation(summary = "Delete user by ID", description = "Deletes the specific user's account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
    })
    @NoUserFoundByIdDoc
    @AdminForbiddenDoc
    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");  // 200 OK with message
    }
}
