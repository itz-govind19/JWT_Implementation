package admin.myapp.com.authservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.ExpiredJwtException;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        String message = "Authentication failed";
        String errorCode = "AUTHENTICATION_ERROR";

        // Check for JWT error attributes set by the filter
        String jwtError = (String) request.getAttribute("jwt_error");
        String jwtErrorMessage = (String) request.getAttribute("jwt_error_message");

        if (jwtError != null && jwtErrorMessage != null) {
            message = jwtErrorMessage;
            switch (jwtError) {
                case "expired":
                    errorCode = "TOKEN_EXPIRED";
                    break;
                case "signature":
                    errorCode = "INVALID_SIGNATURE";
                    break;
                case "malformed":
                    errorCode = "MALFORMED_TOKEN";
                    break;
                case "invalid":
                    errorCode = "INVALID_TOKEN";
                    break;
                case "processing":
                    errorCode = "PROCESSING_ERROR";
                    break;
            }
        }
        // Fallback: Check the cause of the authentication exception
        else {
            Throwable cause = authException.getCause();

            // Handle BadCredentialsException which wraps JWT exceptions
            if (authException instanceof BadCredentialsException) {
                if (cause instanceof ExpiredJwtException) {
                    message = "JWT token has expired";
                    errorCode = "TOKEN_EXPIRED";
                } else if (cause instanceof SignatureException) {
                    message = "JWT token signature is invalid";
                    errorCode = "INVALID_SIGNATURE";
                } else if (cause instanceof MalformedJwtException) {
                    message = "JWT token is malformed";
                    errorCode = "MALFORMED_TOKEN";
                } else if (cause instanceof JwtException) {
                    message = "JWT token is invalid";
                    errorCode = "INVALID_TOKEN";
                } else {
                    message = authException.getMessage();
                    errorCode = "INVALID_CREDENTIALS";
                }
            }
            // Check if the exception message contains JWT-related keywords
            else if (authException.getMessage() != null) {
                String exceptionMessage = authException.getMessage().toLowerCase();
                if (exceptionMessage.contains("expired")) {
                    message = "JWT token has expired";
                    errorCode = "TOKEN_EXPIRED";
                } else if (exceptionMessage.contains("signature")) {
                    message = "JWT token signature is invalid";
                    errorCode = "INVALID_SIGNATURE";
                } else if (exceptionMessage.contains("malformed")) {
                    message = "JWT token is malformed";
                    errorCode = "MALFORMED_TOKEN";
                } else if (exceptionMessage.contains("jwt") || exceptionMessage.contains("token")) {
                    message = "JWT token is invalid";
                    errorCode = "INVALID_TOKEN";
                } else {
                    message = authException.getMessage();
                }
            }
            // Check the cause directly for JWT exceptions
            else if (cause != null) {
                if (cause instanceof ExpiredJwtException) {
                    message = "JWT token has expired";
                    errorCode = "TOKEN_EXPIRED";
                } else if (cause instanceof SignatureException) {
                    message = "JWT token signature is invalid";
                    errorCode = "INVALID_SIGNATURE";
                } else if (cause instanceof MalformedJwtException) {
                    message = "JWT token is malformed";
                    errorCode = "MALFORMED_TOKEN";
                } else if (cause instanceof JwtException) {
                    message = "JWT token is invalid";
                    errorCode = "INVALID_TOKEN";
                }
            }
            // Handle other authentication exceptions
            else {
                message = authException.getMessage() != null ? authException.getMessage() : "Authentication failed";
            }
        }

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED); // 401
        body.put("error", "Unauthorized");
        body.put("message", message);
        body.put("errorCode", errorCode);
        body.put("path", request.getRequestURI());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(objectMapper.writeValueAsString(body));
    }
}