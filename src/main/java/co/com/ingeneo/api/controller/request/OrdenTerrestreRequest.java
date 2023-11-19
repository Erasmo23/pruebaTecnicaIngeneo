package co.com.ingeneo.api.controller.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import co.com.ingeneo.api.config.JsonLocalDateDeserializer;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrdenTerrestreRequest {
	
	@NotNull(message = "No puede estar vacio el campo productoId")
	private Long productoId;
	
	@NotNull(message = "No puede estar vacio el campo clienteId")
	private Long clienteId;

	@NotNull(message = "No puede estar vacio el campo cantidadProducto")
	private Integer cantidadProducto;
	
	@NotNull(message = "No puede estar vacio el campo precioEnvio")
	@Digits(integer = 10, fraction = 2, message = "El campo precioEnvio soporta un total de 8 numeros enteros y 2 en su parte decimal")
    private BigDecimal precioEnvio;

	@NotNull(message = "No puede estar vacio el campo fechaEntrega")
	@Future(message = "La fechaEntrega debe ser posterior a la fecha presente")
	@JsonDeserialize(using = JsonLocalDateDeserializer.class)  
	private LocalDate fechaEntrega;

	@NotNull(message = "No puede estar vacio el campo destinoEntregaId")
	private Long destinoEntregaId;

	@NotBlank(message = "No puede estar vacio el campo placaVehiculo")
	@Size(max = 20, message = "El campo placaVehiculo excede la longitud permitida")
	@Pattern(regexp = "^[A-Za-z]{3}\\d{3}$", message = "El campo numeroFlota debe de tener un patron como el siguiente AAA999 (3 letras iniciales y 3 numeros)")
	private String placaVehiculo;
	
}