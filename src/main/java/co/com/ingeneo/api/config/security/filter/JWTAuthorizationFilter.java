package co.com.ingeneo.api.config.security.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import co.com.ingeneo.api.config.security.service.JWTService;
import co.com.ingeneo.api.config.security.service.JWTServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private final JWTService jwtService;
	
	@Override
	protected void doFilterInternal(final HttpServletRequest request,final HttpServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		final String header = request.getHeader(JWTServiceImpl.HEADER_STRING);

		if (StringUtils.isEmpty(header) || !StringUtils.startsWith(header, JWTServiceImpl.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		
		final String token = header.substring(7);

		final String userEmail = jwtService.getUsername(token);
		
		UsernamePasswordAuthenticationToken authentication = null;

		if (jwtService.validate(token)) {
			authentication = new UsernamePasswordAuthenticationToken(userEmail, null,
					jwtService.getRoles(token));
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		chain.doFilter(request, response);

	}

}