package co.com.ingeneo.api.service;

import java.io.IOException;

import co.com.ingeneo.api.controller.request.RegistroUsuarioRequest;
import co.com.ingeneo.api.controller.request.SignInRequest;
import co.com.ingeneo.api.controller.response.JwtAuthenticationResponse;
import co.com.ingeneo.api.controller.response.UsuarioModel;

public interface AuthenticationService {

	JwtAuthenticationResponse login(final SignInRequest signInRequest) throws IOException;
	
	UsuarioModel registroUser(final RegistroUsuarioRequest registroUsuarioRequest);
	
}