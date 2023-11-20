package co.com.ingeneo.api.controller.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class SignInRequestTestValidation {
	
	private SignInRequest signInRequest;
	
	private ValidatorFactory factory;
	

	@BeforeEach
	void setUp() throws Exception {
		
		signInRequest = SignInRequest.builder().correo("pruebaUnitaria@gmail.com").password("ContrasenniaSeguraTest").build();
		
		factory = Validation.buildDefaultValidatorFactory();
	}

	@Test
	void testValidacionesSinErrores() {
		 final Validator validator = factory.getValidator();
		 
		 Set<ConstraintViolation<SignInRequest>> constraintViolations = validator.validate(signInRequest);
	     assertThat(constraintViolations.size()).isZero();
		 
	}
	
	@Test
	void testSubdomioCorreoValidacionesSinErrores() {
		 final Validator validator = factory.getValidator();
		 signInRequest = SignInRequest.builder().correo("pruebaUnitaria@ingeneo.com.co").password("ContrasenniaSeguraTest").build();
		 
		 Set<ConstraintViolation<SignInRequest>> constraintViolations = validator.validate(signInRequest);
	     assertThat(constraintViolations.size()).isZero();
		 
	}
	
	@Test
	void testSubdomioCorreo2ValidacionesSinErrores() {
		 final Validator validator = factory.getValidator();
		 signInRequest = SignInRequest.builder().correo("pruebaUnitaria@udb.edu.sv").password("ContrasenniaSeguraTest").build();
		 
		 Set<ConstraintViolation<SignInRequest>> constraintViolations = validator.validate(signInRequest);
	     assertThat(constraintViolations.size()).isZero();
		 
	}
	
	@Test
	void testValidacionesCamposRequeridos() {
		 final Validator validator = factory.getValidator();
		 
		 Set<ConstraintViolation<SignInRequest>> constraintViolations = validator.validate(SignInRequest.builder().build());
	     assertEquals(2, constraintViolations.size());
		 
	}
	
	@Test
	void testValidacionesCorreoMalFormatead() {
		 final Validator validator = factory.getValidator();
		 
		 signInRequest.setCorreo("correoMalo");
		 Set<ConstraintViolation<SignInRequest>> constraintViolations = validator.validate(signInRequest);
	     assertEquals(1, constraintViolations.size());
	     
	     constraintViolations.stream().findFirst().ifPresent( constraint -> {
	    	 assertEquals( "El correo debe tener un formato valido", constraint.getMessageTemplate());
	     });
		 
	}

}
