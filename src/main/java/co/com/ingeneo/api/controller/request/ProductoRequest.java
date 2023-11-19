package co.com.ingeneo.api.controller.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
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
public class ProductoRequest {

    @NotBlank(message = "No puede estar vacio el campo nombre")
    @Size(max = 100, message = "El campo nombre excede la longitud permitida")
    private String nombre; 

    @Size(max = 300, message = "El campo descripcion excede la longitud permitida")
    private String descripcion; 

    @Digits(integer = 8, fraction = 2, message = "El campo peso soporta un total de 8 numeros enteros y 2 en su parte decimal")
    private BigDecimal peso; 
	
}