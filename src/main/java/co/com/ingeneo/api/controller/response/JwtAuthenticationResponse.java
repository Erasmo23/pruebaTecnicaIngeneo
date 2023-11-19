package co.com.ingeneo.api.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder()
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {

	@Builder.Default
	private String tokenType = "Bearer";
	private String token;

}