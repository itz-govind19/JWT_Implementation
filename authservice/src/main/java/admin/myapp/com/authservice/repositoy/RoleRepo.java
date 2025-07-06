package admin.myapp.com.authservice.repositoy;

import admin.myapp.com.authservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Role entity.
 * Extends JpaRepository to provide CRUD operations on Role entities.
 *
 * Note: The primary key type of Role is String (role name).
 */
@Repository
public interface RoleRepo extends JpaRepository<Role, String> {

}
