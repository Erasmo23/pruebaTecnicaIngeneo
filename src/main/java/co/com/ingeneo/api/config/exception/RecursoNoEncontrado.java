package co.com.ingeneo.api.config.exception;

import jakarta.persistence.EntityNotFoundException;

public class RecursoNoEncontrado extends EntityNotFoundException {

	private static final long serialVersionUID = -4113858238735418604L;

	public RecursoNoEncontrado(final String message, final Throwable cause) {
		super(message, new Exception(cause));
	}

	public RecursoNoEncontrado(final String message) {
		super(message);
	}
	
}