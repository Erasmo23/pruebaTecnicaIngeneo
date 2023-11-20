package co.com.ingeneo.api.controller.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class OrdenTerrestreRequestTestValidations {

	private OrdenTerrestreRequest ordenTerrestreRequest;

	private ValidatorFactory factory;
	
	private Map<String, String> mapFieldErrorsTemplate;
	
	@BeforeEach
	void setUp() throws Exception {

		ordenTerrestreRequest = OrdenTerrestreRequest.builder().productoId(1L).clienteId(2L).cantidadProducto(12)
				.precioEnvio(new BigDecimal("12.25")).fechaEntrega(LocalDate.now().plusDays(2L)).destinoEntregaId(5L)
				.placaVehiculo("ABC123").build();

		factory = Validation.buildDefaultValidatorFactory();
		mapFieldErrorsTemplate = new HashMap<>();
	}

	@Test
	void testRequestValid() {
		final Validator validator = factory.getValidator();

		Set<ConstraintViolation<OrdenTerrestreRequest>> constraintViolations = validator
				.validate(ordenTerrestreRequest);
		assertThat(constraintViolations.size()).isZero();
	}
	
	
	@Test
	void testRequestErroresVarios() {
		final Validator validator = factory.getValidator();
		
		ordenTerrestreRequest.setFechaEntrega(LocalDate.now());
		ordenTerrestreRequest.setClienteId(null);
		
		mapFieldErrorsTemplate.put("fechaEntrega", "La fechaEntrega debe ser posterior a la fecha presente");
		mapFieldErrorsTemplate.put("clienteId", "No puede estar vacio el campo clienteId");
		

		Set<ConstraintViolation<OrdenTerrestreRequest>> constraintViolations = validator
				.validate(ordenTerrestreRequest);
		
		assertNotNull(constraintViolations);
		
		constraintViolations.forEach(violation ->{
			
			assertEquals(mapFieldErrorsTemplate.get(violation.getPropertyPath().toString()), violation.getMessageTemplate()); 
			
		});
		
	}
	
	@Test
	void testRequestErroresPlacaVehiculoPrecioEnvio() {
		final Validator validator = factory.getValidator();
		
		ordenTerrestreRequest.setPlacaVehiculo("KJFHG");
		ordenTerrestreRequest.setPrecioEnvio(new BigDecimal("123.2545"));
		
		mapFieldErrorsTemplate.put("placaVehiculo", "El campo numeroFlota debe de tener un patron como el siguiente AAA999 (3 letras iniciales y 3 numeros)");
		mapFieldErrorsTemplate.put("precioEnvio", "El campo precioEnvio soporta un total de 8 numeros enteros y 2 en su parte decimal");
		

		Set<ConstraintViolation<OrdenTerrestreRequest>> constraintViolations = validator
				.validate(ordenTerrestreRequest);
		
		assertNotNull(constraintViolations);
		
		constraintViolations.forEach(violation ->{
			
			assertEquals(mapFieldErrorsTemplate.get(violation.getPropertyPath().toString()), violation.getMessageTemplate()); 
			
		});
		
	}

}