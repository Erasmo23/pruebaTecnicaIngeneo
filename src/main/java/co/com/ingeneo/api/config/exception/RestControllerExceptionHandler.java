package co.com.ingeneo.api.config.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		final ApiErrorResponseWrapper apiErrorWrapper = processErrors(ex.getBindingResult().getAllErrors());

        return handleExceptionInternal(ex, apiErrorWrapper, headers, HttpStatus.BAD_REQUEST,
                request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		final ApiErrorResponseWrapper apiErrorWrapper = message((HttpStatus) status, ex);
		return handleExceptionInternal(ex, apiErrorWrapper, headers, status,request);
	}
	
	@ExceptionHandler({IllegalStateException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponseWrapper handleIllegalStateException(final IllegalStateException ex,final WebRequest request) {
		return message(HttpStatus.BAD_REQUEST, ex);
	}
	
	@ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException ex,final HttpStatusCode status,final WebRequest request) {
		final ApiErrorResponseWrapper apiErrorWrapper = message((HttpStatus) status, ex);
        return handleExceptionInternal(ex, apiErrorWrapper, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
	
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiErrorResponseWrapper handleBadCredentialsExceptionHandler(BadCredentialsException ex, HttpServletRequest request){
		return message(HttpStatus.UNAUTHORIZED, ex);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrorResponseWrapper handleRecursoNoEncontrado(EntityNotFoundException ex, WebRequest request){
		
		return message(HttpStatus.NOT_FOUND, ex);
	}
	
	@ExceptionHandler(CustomValidationException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ApiErrorResponseWrapper handleCustomValidationException(CustomValidationException ex, WebRequest request){
		return message(HttpStatus.UNPROCESSABLE_ENTITY, ex);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiErrorResponseWrapper handleAccessDeniedException(AccessDeniedException ex, WebRequest request){
		return message(HttpStatus.UNAUTHORIZED, ex);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiErrorResponseWrapper handleExpiredJwtException(ExpiredJwtException ex, WebRequest request){
		return message(HttpStatus.UNAUTHORIZED, ex);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		logger.error(ex.getMessage(), ex);

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(statusCode)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        if (Objects.isNull(body)) {
            final ApiErrorResponseWrapper apiErrors = message((HttpStatus) statusCode, ex);
            return new ResponseEntity<>(apiErrors, headers, statusCode);
        }

        return new ResponseEntity<>(body, headers, statusCode);
	}
	
	private ApiErrorResponseWrapper message(final HttpStatus httpStatus, final Exception ex) {
        return message(buildApiError(httpStatus, ex));
    }

    private ApiErrorResponseWrapper message(final ApiErrorResponse error) {
        final ApiErrorResponseWrapper errors = new ApiErrorResponseWrapper();
        errors.addApiError(error);
        return errors;
    }
	
	/**
     * Build ApiErrorWrapper DTO.
     * 
     * @param errors of request
     * @return API ApiErrorWrapper DTO
     */
    private ApiErrorResponseWrapper processErrors(final List<ObjectError> errors) {
        final ApiErrorResponseWrapper dto = new ApiErrorResponseWrapper();
        errors.forEach(objError -> {
            if (isFieldError(objError)) {
                FieldError fieldError = (FieldError) objError;
                final String localizedErrorMessage = fieldError.getDefaultMessage();
                dto.addFieldError(fieldError.getClass().getSimpleName(), "Invalid Attribute",
                        fieldError.getField(), localizedErrorMessage);
            } else {
                final String localizedErrorMessage = objError.getDefaultMessage();
                dto.addFieldError(objError.getClass().getSimpleName(), "Invalid Attribute", "base",
                        localizedErrorMessage);
            }
        });

        return dto;
    }
	
	private ApiErrorResponse buildApiError(final HttpStatus httpStatus, final Exception ex) {

        final String typeException = ex.getClass().getSimpleName();
        String description = StringUtils.defaultIfBlank(ex.getMessage(), ex.getClass().getSimpleName());
        String source = "base";

        if (isMissingRequestParameterException(ex)) {

            MissingServletRequestParameterException missingParamEx =
                    (MissingServletRequestParameterException) ex;

            source = missingParamEx.getParameterName();
        } else if (isMissingPathVariableException(ex)) {

            MissingPathVariableException missingPathEx = (MissingPathVariableException) ex;

            source = missingPathEx.getVariableName();
        }
        
        if (isAccessDeniedException(ex)) {
        	description = "Se nesesitan mas privilegios para acceder a este recurso";
        }
        
        if (isBadCredentialsException(ex)) {
        	description = "Direccion de correo electronico o contraseña incorrectos.";
        }

        return ApiErrorResponse
                .builder()
                    .statusCode(httpStatus.value())
                    .type(typeException)
                    .message(httpStatus.getReasonPhrase())
                    .description(description)
                    .source(source)
                    .localDateTime(LocalDateTime.now())
                .build();
    }

    private boolean isMissingPathVariableException(final Exception ex) {
        return ex instanceof MissingPathVariableException;
    }

    private boolean isMissingRequestParameterException(final Exception ex) {
        return ex instanceof MissingServletRequestParameterException;
    }
    
    private boolean isAccessDeniedException(final Exception ex) {
        return ex instanceof AccessDeniedException;
    }
    
    private boolean isBadCredentialsException(final Exception ex) {
    	return ex instanceof BadCredentialsException;
    }
    

    private boolean isFieldError(ObjectError objError) {
        return objError instanceof FieldError;
    }
	
}