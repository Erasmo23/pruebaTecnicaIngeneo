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
public class RegistroUsuarioRequest {

	@NotBlank(message = "No puede estar vacio el campo nombres")
	@Size(max = 100, message = "El campo nombres excede la longitud permitida")
    private String nombres; 

    @Size(max = 100, message = "El campo apellidos excede la longitud permitida")
    private String apellidos; 

    @NotBlank(message = "No puede estar vacio el campo correo")
    @Size(max = 100, message = "El campo correo excede la longitud permitida")
    @Email
    private String correo; 

    @NotBlank(message = "No puede estar vacio el campo password")
    @Size(max = 100,min = 8, message = "El campo password debe de contener al menos 8 caracteres o un maximo de 100 caracteres")
    private String password;
    
    @NotBlank(message = "No puede estar vacio el campo password")
    @Size(max = 100, message = "El campo password excede la longitud permitida")
    private String passwordConfirm;
	 
}