package co.com.ingeneo.api.service.implementation;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.ingeneo.api.config.exception.CustomValidationException;
import co.com.ingeneo.api.config.security.service.JWTService;
import co.com.ingeneo.api.controller.request.RegistroUsuarioRequest;
import co.com.ingeneo.api.controller.request.SignInRequest;
import co.com.ingeneo.api.controller.response.JwtAuthenticationResponse;
import co.com.ingeneo.api.controller.response.UsuarioModel;
import co.com.ingeneo.api.repository.SecUsuarioCreateTokenRepository;
import co.com.ingeneo.api.repository.SecUsuarioRepository;
import co.com.ingeneo.api.repository.SecUsuarioRolRepository;
import co.com.ingeneo.api.repository.domain.SecRol;
import co.com.ingeneo.api.repository.domain.SecUsuario;
import co.com.ingeneo.api.repository.domain.SecUsuarioCreateToken;
import co.com.ingeneo.api.repository.domain.SecUsuarioRol;
import co.com.ingeneo.api.service.AuthenticationService;
import co.com.ingeneo.api.service.assembler.SecUSuarioAssembler;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private final JWTService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	private final SecUsuarioRepository secUsuarioRepository;
	
	private final SecUsuarioRolRepository secUsuarioRolRepository;
	
	private final SecUsuarioCreateTokenRepository secUsuarioCreateTokenRepository;
	
	private final SecUSuarioAssembler secUSuarioAssembler;
	
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	@Override
	public JwtAuthenticationResponse login(final SignInRequest signInRequest) throws IOException {

		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					signInRequest.getCorreo(), signInRequest.getPassword()
				));
			
		return JwtAuthenticationResponse.builder().token(jwtService.create(auth)).build();
	}

	@Transactional
	@Override
	public UsuarioModel registroUser(final RegistroUsuarioRequest registroUsuarioRequest) {
		
		if ( !StringUtils.equals(registroUsuarioRequest.getPassword(), registroUsuarioRequest.getPasswordConfirm()) ) {
			throw new CustomValidationException("Contraseñas no coinciden");
		}
		
		if (secUsuarioRepository.countByCorreo(registroUsuarioRequest.getCorreo()) > 0) {
			throw new CustomValidationException("Correo ya fue ingresado en el sistema");
		}
		
		SecUsuario secUsuario = secUSuarioAssembler.getSecUsuarioMapper().toSecUsuario(registroUsuarioRequest);
		
		secUsuario.setPassword(passwordEncoder.encode(registroUsuarioRequest.getPassword()));
		secUsuario.setActivo(Boolean.FALSE);
		
		
		secUsuarioRepository.save(secUsuario);
		
		SecRol secRolCommon = secUsuarioRolRepository.findSecRolByCodigo("COMMON_USER");
		
		SecUsuarioRol secUsuarioRolCommon = new SecUsuarioRol();
		
		secUsuarioRolCommon.setSecRol(secRolCommon);
		secUsuarioRolCommon.setSecUsuario(secUsuario);
		
		secUsuarioRolRepository.save(secUsuarioRolCommon);
		
		SecUsuarioCreateToken secUsuarioCreateToken = new SecUsuarioCreateToken(secUsuario);
		
		secUsuarioCreateTokenRepository.save(secUsuarioCreateToken);
		
		secUsuario.setTokenTemp(secUsuarioCreateToken.getToken());
		
		return secUSuarioAssembler.toModel(secUsuario);
	}

}
