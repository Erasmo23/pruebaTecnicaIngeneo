package co.com.ingeneo.api.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class SignInRequest {

	@NotNull(message = "El campo de correo no puede ser nulo")
	@Pattern(regexp = "[a-zA-Z0-9._%\\-]+@[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,5}(\\.[a-zA-Z]{2,5})?", message = "El correo debe tener un formato valido")
	private String correo;
	
	@NotNull(message = "El campo de password no puede ser nulo")
	private String password;
	
	
}