package co.com.ingeneo.api.controller.request;

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
public class TipoTransporteRequest {
	
    @NotBlank(message = "No puede estar vacio el campo codigo")
    @Size(max = 10, message = "El campo codigo excede la longitud permitida")
    private String codigo; 

    @NotBlank(message = "No puede estar vacio el campo descripcion")
    @Size(max = 50, message = "El campo descripcion excede la longitud permitida")
    private String descripcion; 

    @NotBlank(message = "No puede estar vacio el campo labelApp")
    @Size(max = 25, message = "El campo labelApp excede la longitud permitida")
    private String labelApp; 

}