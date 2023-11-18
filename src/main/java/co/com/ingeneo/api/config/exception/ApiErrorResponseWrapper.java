package co.com.ingeneo.api.config.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiErrorResponseWrapper {

	private final List<ApiErrorResponse> errors = new ArrayList<>();

	public final void addApiError(final ApiErrorResponse error) {
		errors.add(error);
	}

	public final void addFieldError(final String type, final String message, final String source,
			final String description) {
		final ApiErrorResponse error = ApiErrorResponse.builder().statusCode(HttpStatus.BAD_REQUEST.value()).type(type)
				.message(message).description(description).source(source).localDateTime(LocalDateTime.now()).build();
		errors.add(error);
	}

}