package co.com.ingeneo.api.service.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.Optional;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import co.com.ingeneo.api.controller.ClienteController;
import co.com.ingeneo.api.controller.response.ClienteModel;
import co.com.ingeneo.api.repository.domain.Cliente;
import co.com.ingeneo.api.service.mapper.ClienteMapper;
import lombok.Getter;
import lombok.NonNull;

@Component
public class ClienteAssembler extends RepresentationModelAssemblerSupport<Cliente, ClienteModel> {

	@Getter
	private final @NonNull ClienteMapper clienteMapper;
	
	public ClienteAssembler(final @NonNull ClienteMapper clienteMapper) {
		super(ClienteController.class, ClienteModel.class);
		this.clienteMapper = clienteMapper;
	}

	@Override
	public ClienteModel toModel(Cliente entity) {
		return Optional.ofNullable(entity).map(value -> {
			ClienteModel model = this.clienteMapper.toClienteModel(value);
			model.add(linkTo(getControllerClass()).slash(model.getClienteId()).withSelfRel());
			return model;
		}).orElse(null);
	}
	
}