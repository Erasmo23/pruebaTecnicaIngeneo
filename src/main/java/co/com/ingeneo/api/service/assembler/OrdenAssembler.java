package co.com.ingeneo.api.service.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.Optional;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import co.com.ingeneo.api.controller.OrdenController;
import co.com.ingeneo.api.controller.response.OrdenModel;
import co.com.ingeneo.api.repository.domain.Orden;
import co.com.ingeneo.api.service.mapper.OrdenMapper;
import lombok.Getter;
import lombok.NonNull;

@Component
public class OrdenAssembler extends RepresentationModelAssemblerSupport<Orden, OrdenModel> {

	@Getter
	private final @NonNull OrdenMapper ordenMapper;

	public OrdenAssembler( final @NonNull OrdenMapper ordenMapper) {
		super(OrdenController.class, OrdenModel.class);
		this.ordenMapper = ordenMapper;
	}
	
	@Override
	public OrdenModel toModel(Orden entity) {
		return Optional.ofNullable(entity).map(value -> {
			OrdenModel model = this.ordenMapper.toOrdenModel(value);
			model.add(linkTo(getControllerClass()).slash(model.getOrdenId()).withSelfRel());
			return model;
		}).orElse(null);
	}
	
}