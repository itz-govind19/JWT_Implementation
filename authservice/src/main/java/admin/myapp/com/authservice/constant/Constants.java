package admin.myapp.com.authservice.constant;

/**
 * Contains application-wide constant values for URL base paths and role names.
 */
public class Constants {

    /** Base URL path for authentication-related endpoints */
    public static final String BASE_URL = "/api/v1/auth";

    /** Base URL path for admin-related endpoints */
    public static final String BASE_URL_ADMIN = "/api/v1/admin";

    /** Base URL path for user-related endpoints */
    public static final String BASE_URL_USER = "/api/v1/user";

    /** Role name for administrator users */
    public static final String ADMIN = "ADMIN";

    /** Role name for regular users */
    public static final String USER = "USER";
}
