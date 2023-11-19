package co.com.ingeneo.api.service.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.lang.reflect.Method;
import java.util.Optional;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import co.com.ingeneo.api.controller.AuthenticationController;
import co.com.ingeneo.api.controller.response.UsuarioModel;
import co.com.ingeneo.api.repository.domain.SecUsuario;
import co.com.ingeneo.api.service.mapper.SecUsuarioMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecUSuarioAssembler extends RepresentationModelAssemblerSupport<SecUsuario, UsuarioModel> {

	@Getter
	private final @NonNull SecUsuarioMapper secUsuarioMapper;
	
	public SecUSuarioAssembler(final @NonNull SecUsuarioMapper secUsuarioMapper) {
		super(AuthenticationController.class, UsuarioModel.class);
		this.secUsuarioMapper = secUsuarioMapper;
	}

	@Override
	public UsuarioModel toModel(SecUsuario entity) {
		return Optional.ofNullable(entity).map(value -> {
			UsuarioModel model = this.secUsuarioMapper.toUsuarioModel(value);
			
			try {
				Method methodActive = AuthenticationController.class.getMethod("active", String.class);
				Link link = linkTo(methodActive, model.getTokenActivate()).withSelfRel();
				model.add(link);
			} catch (NoSuchMethodException | SecurityException e) {
				log.error("Error creando links", e);
				model.add(linkTo(getControllerClass()).slash(model.getUsuarioId()).withSelfRel());
			}
			
			return model;
		}).orElse(null);
	}
	
}