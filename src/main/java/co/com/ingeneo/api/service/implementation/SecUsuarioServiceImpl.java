package co.com.ingeneo.api.service.implementation;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.ingeneo.api.config.exception.RecursoNoEncontrado;
import co.com.ingeneo.api.controller.request.UsuarioRolRequest;
import co.com.ingeneo.api.controller.response.SelectOptionGeneric;
import co.com.ingeneo.api.repository.SecRolRepository;
import co.com.ingeneo.api.repository.SecUsuarioCreateTokenRepository;
import co.com.ingeneo.api.repository.SecUsuarioRepository;
import co.com.ingeneo.api.repository.SecUsuarioRolRepository;
import co.com.ingeneo.api.repository.domain.SecUsuario;
import co.com.ingeneo.api.repository.domain.SecUsuarioCreateToken;
import co.com.ingeneo.api.repository.domain.SecUsuarioRol;
import co.com.ingeneo.api.service.SecUsuarioService;
import co.com.ingeneo.api.service.assembler.SelectOptionAssembler;
import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SecUsuarioServiceImpl implements SecUsuarioService {

	@NonNull
	private final SecUsuarioCreateTokenRepository secUsuarioCreateTokenRepository;
	
	@NonNull
	private final SecUsuarioRolRepository secUsuarioRolRepository;
	
	@NonNull
	private final SecUsuarioRepository secUsuarioRepository;
	
	@NonNull
	private final SecRolRepository secRolRepository;
	
	@Override
	public void active(final String token) {
		SecUsuarioCreateToken secUsuarioCreateToken = secUsuarioCreateTokenRepository.findByToken(token).orElseThrow(
				()-> new RecursoNoEncontrado("El token que fue generado en la creaci&oacute;n del usuario no coincide con el enviado"));
		
		SecUsuario secUsuario = secUsuarioCreateToken.getSecUsuario();
		secUsuario.setActivo(Boolean.TRUE);
		secUsuarioRepository.save(secUsuario);
		
	}

	@Override
	public void deleteUsuarioRol(final Long idUsuarioRol) {
		secUsuarioRolRepository.deleteById(idUsuarioRol);
	}

	@Override
	public void addUsuarioRol(final UsuarioRolRequest usuarioRolRequest) {
		
		SecUsuarioRol secUsuarioRol = new SecUsuarioRol();
		secUsuarioRol.setSecUsuario(secUsuarioRepository.getReferenceById(usuarioRolRequest.getUsuarioId()));
		secUsuarioRol.setSecRol(secRolRepository.getReferenceById(usuarioRolRequest.getRolId()));
		
		secUsuarioRolRepository.save(secUsuarioRol);
		
	}

	@Transactional(readOnly = true)
	@Override
	public CollectionModel<SelectOptionGeneric> selectUsuarios(final Class<?> controller) {
		return SelectOptionAssembler.toCollectionModelGeneric(secUsuarioRepository.findAll(), 
				usuario -> new SelectOptionGeneric(usuario.getId().toString(), usuario.nombreCompleto()), 
				controller);
	}

	@Transactional(readOnly = true)
	@Override
	public CollectionModel<SelectOptionGeneric> selecRoles(Class<?> controller, String method) {
		return SelectOptionAssembler.toCollectionModelGeneric(secRolRepository.findAll(), 
				rol -> new SelectOptionGeneric(rol.getId().toString(), rol.codigoWithdescripcion()),controller, method);
	}

}