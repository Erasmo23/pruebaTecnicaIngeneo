package co.com.ingeneo.api.config.security;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ingeneo.api.config.exception.ApiErrorResponse;
import co.com.ingeneo.api.config.exception.ApiErrorResponseWrapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -6578140785219999726L;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		logger.error("Responding with unauthorized error. Message - {}", authException.getMessage());
		
		ApiErrorResponseWrapper errores = new ApiErrorResponseWrapper();
		
		ApiErrorResponse error = ApiErrorResponse.builder().statusCode(HttpStatus.UNAUTHORIZED.value())
                .type(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .description(authException.getMessage())
                .localDateTime(LocalDateTime.now())
            .build();
		
		errores.addApiError(error);
		
		response.setHeader("content-type", "application/json");
		response.getWriter().write(objectMapper.writeValueAsString(errores));
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}

}