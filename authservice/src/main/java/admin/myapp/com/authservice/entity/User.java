package admin.myapp.com.authservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a user in the system.
 * Includes validation constraints and mapping to the "users" table.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users") // ✅ Optional but good practice
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Recommended for databases
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;


    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Column(nullable = false)
    private String password;


    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    @Column(nullable = false)
    private String mobile;

    /**
     * Roles assigned to the user.
     * Mapped with a join table "user_roles" linking users and roles by IDs.
     * FetchType.EAGER ensures roles are loaded with user.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // ✅ join table name
            joinColumns = @JoinColumn(name = "user_id"), // FK to user
            inverseJoinColumns = @JoinColumn(name = "role_name") // FK to role (since role's PK is name)
    )
    private Set<Role> roles = new HashSet<>();
}
