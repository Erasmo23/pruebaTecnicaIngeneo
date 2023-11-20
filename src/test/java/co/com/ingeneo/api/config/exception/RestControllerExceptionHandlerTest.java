package co.com.ingeneo.api.config.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;

@ActiveProfiles("test")
class RestControllerExceptionHandlerTest {

	private RestControllerExceptionHandler restControllerExceptionHandler;

	private WebRequest webRequest;

	private HttpServletRequest httpServletRequest;

	@BeforeEach
	public void setup() {
		webRequest = mock(WebRequest.class);
		httpServletRequest = mock(HttpServletRequest.class);
		restControllerExceptionHandler = new RestControllerExceptionHandler();
	}

	@Test
	public void retornoErrorAccessDeniedExceptionHandled() {

		AccessDeniedException exception = new AccessDeniedException("index.html");

		ApiErrorResponseWrapper response = restControllerExceptionHandler.handleAccessDeniedException(exception,
				webRequest);

		assertNotNull(response);
		assertEquals(1, response.getErrors().size());

		assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getErrors().get(0).getStatusCode());
		assertEquals("Se nesesitan mas privilegios para acceder a este recurso",
				response.getErrors().get(0).getDescription());

	}

	@Test
	public void retornoErrorBadCredentialsExceptionHandled() {

		BadCredentialsException exception = new BadCredentialsException("sin Privilegios");

		ApiErrorResponseWrapper response = restControllerExceptionHandler
				.handleBadCredentialsExceptionHandler(exception, httpServletRequest);

		assertNotNull(response);
		assertEquals(1, response.getErrors().size());

		assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getErrors().get(0).getStatusCode());
		assertEquals("Direccion de correo electronico o contraseña incorrectos.",
				response.getErrors().get(0).getDescription());

	}

}