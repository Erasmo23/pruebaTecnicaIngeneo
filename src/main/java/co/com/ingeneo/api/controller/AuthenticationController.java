package co.com.ingeneo.api.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.com.ingeneo.api.controller.request.RegistroUsuarioRequest;
import co.com.ingeneo.api.controller.request.SignInRequest;
import co.com.ingeneo.api.controller.response.JwtAuthenticationResponse;
import co.com.ingeneo.api.controller.response.UsuarioModel;
import co.com.ingeneo.api.service.AuthenticationService;
import co.com.ingeneo.api.service.SecUsuarioService;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	@NonNull
	private final AuthenticationService authenticationService;
	
	@NonNull
	private final SecUsuarioService secUsuarioService;
	
	@PatchMapping("/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void active(@RequestParam("tokenActivate") String tokenActivate) {
		secUsuarioService.active(tokenActivate);
	}

	@PostMapping("/registro")
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel registro(@RequestBody @Valid RegistroUsuarioRequest registroUsuarioRequest) {
		return authenticationService.registroUser(registroUsuarioRequest);
	}
	
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public JwtAuthenticationResponse login(@RequestBody @Valid SignInRequest signInRequest) throws IOException {
		return authenticationService.login(signInRequest);
	}
	
}