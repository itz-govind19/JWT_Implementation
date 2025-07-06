package admin.myapp.com.authservice.controller;

import admin.myapp.com.authservice.annotations.*;
import admin.myapp.com.authservice.constant.Constants;
import admin.myapp.com.authservice.dto.AuthResponse;
import admin.myapp.com.authservice.dto.LoginRequest;
import admin.myapp.com.authservice.dto.RegisterRequest;
import admin.myapp.com.authservice.entity.User;
import admin.myapp.com.authservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST controller that handles authorization-related endpoints such as user registration,
 * login, and JWT token validation.
 */
@RestController
@Tag(name = "Auth Controller", description = "Handles Authorization operations")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@RequestMapping(Constants.BASE_URL)
public class AuthController {


    @Autowired
    private UserService userService;

    /**
     * Registers a new user with the provided details.
     *
     * @param request the registration request containing user details
     * @return ResponseEntity containing the registered User on success or an error response on failure
     */
    @Operation(summary = "Register a new user", description = "This endpoint allows a user to register by providing their details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = User.class))),
    })
    @RoleNotFoundDoc
    @DuplicateUserDoc
    @BadRequestDoc
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException ex) {
            String message = ex.getMessage();
            HttpStatus status = HttpStatus.BAD_REQUEST;

            if ("Username already exists".equals(message)) {
                status = HttpStatus.CONFLICT;
            } else if (message.startsWith("Role not found")) {
                status = HttpStatus.NOT_FOUND;
            }

            return new ResponseEntity<>(
                    new admin.myapp.com.authservice.dto.ApiResponse(status.value(), message, LocalDateTime.now()),
                    status
            );
        }
    }

    /**
     * Authenticates a user and returns a JWT token if the credentials are valid.
     *
     * @param request the login request containing username and password
     * @return ResponseEntity containing an AuthResponse with the JWT token
     */
    @Operation(summary = "Login user", description = "This endpoint allows a user to log in using their credentials and receive a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful and token generated",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthResponse.class))),
    })
    @PostMapping("/login")
    @InvalidCredentialDoc
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = userService.authenticate(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }


    /**
     * Validates the provided JWT token.
     *
     * @param token the JWT token to validate (passed as a query parameter)
     * @return ResponseEntity with a validation message or error
     */
    @Operation(summary = "Validate JWT Token", description = "This endpoint validates a provided JWT token and returns the validation result.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token is valid"),
    })
    @InvalidTokenDoc
    @GetMapping("/validate-token")
    public ResponseEntity<?> validate(@RequestParam String token) {
        return ResponseEntity.ok(userService.validateToken(token));
    }

}
