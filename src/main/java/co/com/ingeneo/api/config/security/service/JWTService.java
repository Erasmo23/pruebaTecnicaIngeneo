package co.com.ingeneo.api.config.security.service;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;

public interface JWTService {

	public String create(Authentication auth) throws IOException;
	
	public boolean validate(String token);
	
	public Claims extractAllClaims(String token);

	public String getUsername(String token);

	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;

}