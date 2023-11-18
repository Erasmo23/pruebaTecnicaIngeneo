package co.com.ingeneo.api.controller.request;

import jakarta.validation.constraints.Email;
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
public class ClienteRequest {

	@NotBlank(message = "No puede estar vacio el campo nombres")
    @Size(max = 100, message = "El campo nombres excede la longitud permitida")
    private String nombres;

    @Size(max = 100, message = "El campo apellidos excede la longitud permitida")
    private String apellidos;

    @NotBlank(message = "No puede estar vacio el campo correo")
    @Size(max = 100, message = "El campo correo excede la longitud permitida")
    @Email
    private String correo;

    @Size(max = 20, message = "El campo telefono excede la longitud permitida")
    private String telefono;

    @NotBlank(message = "No puede estar vacio el campo direccion")
    @Size(max = 200, message = "El campo direccion excede la longitud permitida")
    private String direccion;

    @Size(max = 200, message = "El campo direccionFacturacion excede la longitud permitida")
    private String direccionFacturacion;

	
}