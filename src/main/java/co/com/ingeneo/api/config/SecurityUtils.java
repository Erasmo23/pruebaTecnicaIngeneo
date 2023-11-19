package co.com.ingeneo.api.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public abstract class SecurityUtils {

	public SecurityUtils() {
		throw new IllegalStateException("Util Class must not be instantiated!");
	}
	
	public static final String HAS_AUTHORITY_REGISTER = "hasAnyAuthority('REGISTER_USER','ADMIN_USER')";
    public static final String HAS_AUTHORITY_ADMIN = "hasAuthority('ADMIN_USER')";
    public static final String HAS_AUTHORITY_ORDER = "hasAnyAuthority('ORDER_USER','ADMIN_USER')";
    
    public static User getCurrentUser() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		return user;
	}	
	
}