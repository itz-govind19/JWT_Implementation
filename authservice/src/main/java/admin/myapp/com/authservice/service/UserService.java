package admin.myapp.com.authservice.service;

import admin.myapp.com.authservice.constant.Constants;
import admin.myapp.com.authservice.dto.LoginRequest;
import admin.myapp.com.authservice.dto.RegisterRequest;
import admin.myapp.com.authservice.entity.Role;
import admin.myapp.com.authservice.entity.User;
import admin.myapp.com.authservice.execeptions.*;
import admin.myapp.com.authservice.repositoy.RoleRepo;
import admin.myapp.com.authservice.repositoy.UserRepo;
import admin.myapp.com.authservice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class handling user-related operations such as registration,
 * authentication, user retrieval, updating, and deletion.
 */
@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Registers a new user with the provided registration request.
     *
     * @param request the registration request containing user details and roles
     * @return the registered User entity
     * @throws DuplicateUserException if the username already exists
     * @throws InvalidUserException   if any specified role is not found
     */
    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateUserException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.findById(roleName)
                        .orElseThrow(() -> new InvalidUserException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        userRepository.save(user);
        return user;
    }

    /**
     * Authenticates a user and generates a JWT token upon successful authentication.
     *
     * @param request the login request containing username and password
     * @return a JWT token prefixed with "Bearer "
     * @throws RuntimeException           if the user is not found
     * @throws InvalidUserException       if user has no valid role assigned
     * @throws InvalidCredentialsException if authentication fails
     */
    public String authenticate(LoginRequest request) {
        User byUsername = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NoUserFoundException("User not found with username: " + request.getUsername()));
        Set<Role> roles = byUsername.getRoles();
        Optional<Role> first = roles.stream().findFirst();
        String role = null;
        if (first.isPresent()) {
            role = first.get().getName();
        } else {
            throw new InvalidUserException("Invalid User OR Role not found");
        }
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return "Bearer " + jwtUtil.generateToken(request.getUsername(), role);
    }

    /**
     * Validates the provided JWT token.
     *
     * @param token the JWT token string
     * @return confirmation message if token is valid
     * @throws InvalidTokenException if token is invalid or expired
     */
    public String validateToken(String token) {
        String username = jwtUtil.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtUtil.validateToken(token)) {
            throw new InvalidTokenException("Invalid or expired token");
        }

        return "Token is valid for user: " + username;
    }


    /**
     * Retrieves all users with the ADMIN role.
     *
     * @return list of admin users
     * @throws NoUserFoundException if no admin users are found
     */
    public List<User> getAllAdmins() {

        List<User> byRole = userRepository.findByRoleName(Constants.ADMIN);
        if (byRole.isEmpty()) {
            throw new NoUserFoundException("No admin found");
        }
        return byRole;
    }

    /**
     * Retrieves the current user by username.
     *
     * @param name the username of the current user
     * @return the User entity
     * @throws NoUserFoundException if no user is found with the given username
     */
    public User getCurrentUser(String name) {

        User byRole = userRepository.findByUsername(name).orElseThrow(() -> new NoUserFoundException("User not found with username: " + name));

        if (byRole == null) {
            throw new NoUserFoundException("No User found");
        }
        return byRole;
    }

    /**
     * Retrieves all users.
     *
     * @return list of all users
     */
    public List<User> getAllUser() {

        List<User> byRole = userRepository.findAll();

        return byRole;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the user ID
     * @return the User entity
     * @throws NoUserFoundException if no user is found with the given ID
     */
    public User getUserById(Long id) {

        Optional<User> byRole = userRepository.findById(id);

        if (byRole.isEmpty()) {
            throw new NoUserFoundException("No User found with given Id:: " + id);
        }
        return byRole.get();
    }


    /**
     * Updates user details for the user with the given username.
     *
     * @param username    the username of the user to update
     * @param updatedUser the updated user details
     * @return the updated User entity
     * @throws DuplicateUserException   if the updated username already exists
     * @throws UsernameNotFoundException if the user to update is not found
     */
    public User updateUser(String username, User updatedUser) {
        User existing1 = userRepository.findByUsername(updatedUser.getUsername())
                .orElseThrow(() -> new DuplicateUserException("UserName already exists"));


        User existing = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        existing.setName(updatedUser.getName());
        existing.setRoles(updatedUser.getRoles());
        existing.setEmail(updatedUser.getEmail());
        existing.setMobile(updatedUser.getMobile());
        existing.setPassword(passwordEncoder.encode(existing.getPassword()));
        // Add checks if needed
        return userRepository.save(existing);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @throws UsernameNotFoundException if no user is found with the given ID
     */
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with provided id:: " + id));
        userRepository.delete(user);
    }
}
