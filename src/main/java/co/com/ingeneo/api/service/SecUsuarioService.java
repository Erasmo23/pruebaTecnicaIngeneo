package co.com.ingeneo.api.service;

import org.springframework.hateoas.CollectionModel;

import co.com.ingeneo.api.controller.request.UsuarioRolRequest;
import co.com.ingeneo.api.controller.response.SelectOptionGeneric;

public interface SecUsuarioService {

	public void active(final String token);
	
	public void deleteUsuarioRol(final Long idUsuarioRol);
	
	public void addUsuarioRol(final UsuarioRolRequest usuarioRolRequest);

	public CollectionModel<SelectOptionGeneric> selectUsuarios(Class<?> controller);
	
	public CollectionModel<SelectOptionGeneric> selecRoles(Class<?> controller, String method);
	
}