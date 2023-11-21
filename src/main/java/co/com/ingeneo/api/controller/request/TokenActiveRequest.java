package co.com.ingeneo.api.controller.request;

import jakarta.validation.constraints.NotNull;
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
public class TokenActiveRequest {
	
	@NotNull(message = "El campo de usuarioId no puede ser nulo")
	private String tokenActivate;

}
