package co.com.ingeneo.api.service.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.Optional;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import co.com.ingeneo.api.controller.DestinoEntregaController;
import co.com.ingeneo.api.controller.response.DestinoEntregaModel;
import co.com.ingeneo.api.repository.domain.DestinoEntrega;
import co.com.ingeneo.api.service.mapper.DestinoEntregaMapper;
import lombok.Getter;
import lombok.NonNull;

@Component
public class DestinoEntregaAssembler extends RepresentationModelAssemblerSupport<DestinoEntrega, DestinoEntregaModel> {

	@Getter
	private final @NonNull DestinoEntregaMapper destinoEntregaMapper;
	
	public DestinoEntregaAssembler(final @NonNull DestinoEntregaMapper destinoEntregaMapper) {
		super(DestinoEntregaController.class, DestinoEntregaModel.class);
		this.destinoEntregaMapper = destinoEntregaMapper;
	}

	@Override
	public DestinoEntregaModel toModel(DestinoEntrega entity) {
		return Optional.ofNullable(entity).map(value -> {
			DestinoEntregaModel model = this.destinoEntregaMapper.toDestinoEntregaModel(value);
			model.add(linkTo(getControllerClass()).slash(model.getDestinoEntregaId()).withSelfRel());
			return model;
		}).orElse(null);
	}
	
}