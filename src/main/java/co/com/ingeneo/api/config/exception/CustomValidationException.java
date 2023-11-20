package co.com.ingeneo.api.config.exception;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class CustomValidationException extends NestedRuntimeException {

	private static final long serialVersionUID = 5367790105449902438L;
	
	public CustomValidationException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public CustomValidationException(final String message) {
		super(message);
	}
	
}