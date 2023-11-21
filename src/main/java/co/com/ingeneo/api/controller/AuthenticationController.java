package co.com.ingeneo.api.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.com.ingeneo.api.controller.request.RegistroUsuarioRequest;
import co.com.ingeneo.api.controller.request.SignInRequest;
import co.com.ingeneo.api.controller.request.TokenActiveRequest;
import co.com.ingeneo.api.controller.response.JwtAuthenticationResponse;
import co.com.ingeneo.api.controller.response.UsuarioModel;
import co.com.ingeneo.api.service.AuthenticationService;
import co.com.ingeneo.api.service.SecUsuarioService;
import io.micrometer.common.lang.NonNull;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Controlador Autenticador", description = "EndPoint's encargados del registro y autenticacion de los usuarios de la API" )
@RequiredArgsConstructor
public class AuthenticationController {
	
	@NonNull
	private final AuthenticationService authenticationService;
	
	@NonNull
	private final SecUsuarioService secUsuarioService;
	
	@PatchMapping("/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void active(@RequestBody TokenActiveRequest token) {
		secUsuarioService.active(token.getTokenActivate());
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