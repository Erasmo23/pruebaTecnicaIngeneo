package co.com.ingeneo.api.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import co.com.ingeneo.api.controller.request.RegistroUsuarioRequest;
import co.com.ingeneo.api.controller.response.UsuarioModel;
import co.com.ingeneo.api.repository.domain.SecUsuario;

@Mapper(componentModel = "spring")
public interface SecUsuarioMapper {

	SecUsuarioMapper INSTANCE = Mappers.getMapper( SecUsuarioMapper.class );
	
	@Mappings({
		@Mapping(ignore = true, target = "id"),@Mapping(ignore = true, target = "activo"),
		@Mapping(ignore = true, target = "fechaRegistro"), @Mapping(ignore = true, target = "secUsuarioCreateTokens"),
		@Mapping(ignore = true, target = "tokenTemp"),
		@Mapping(ignore = true, target = "secUsuarioRoles") })
	SecUsuario toSecUsuario(final RegistroUsuarioRequest registroUsuarioRequest);
	
	@Mapping(source = "id", target = "usuarioId")
	@Mapping(source = "tokenTemp", target = "tokenActivate")
	UsuarioModel toUsuarioModel(final SecUsuario secUsuario);
	
}