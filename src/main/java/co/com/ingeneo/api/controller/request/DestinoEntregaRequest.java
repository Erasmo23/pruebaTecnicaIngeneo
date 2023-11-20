package co.com.ingeneo.api.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class DestinoEntregaRequest {
	
	@NotBlank(message = "No puede estar vacio el campo nombre")
    @Size(max = 150, message = "El campo nombre excede la longitud permitida de 150 caracteres")
    private String nombre; 

    @NotBlank(message = "No puede estar vacio el campo direccion")
    @Size(max = 300, message = "El campo direccion excede la longitud permitida de 300 caracteres")
    private String direccion; 

    @Size(max = 100, message = "El campo contacto excede la longitud permitida")
    private String contacto; 

    @Size(max = 20, message = "El campo telefono excede la longitud permitida")
    private String telefono;
    
    @NotNull(message = "El campo de paisId no puede ser nulo")
    private Long paisId;
    
    @NotNull(message = "El campo de tipoTransporteId no puede ser nulo")
    private Long tipoTransporteId;
	
}