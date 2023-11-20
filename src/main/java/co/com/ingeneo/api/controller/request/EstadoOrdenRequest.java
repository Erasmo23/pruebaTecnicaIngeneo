package co.com.ingeneo.api.controller.request;

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
public class EstadoOrdenRequest {

	@Size(max = 10, message = "El campo codigo excede la longitud permitida")
    private String codigo; 

    @Size(max = 100, message = "El campo descripcion excede la longitud permitida")
    private String descripcion; 
	
}