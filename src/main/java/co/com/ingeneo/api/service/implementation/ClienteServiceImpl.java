package co.com.ingeneo.api.service.implementation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.ingeneo.api.config.CommonConfigurationUtils;
import co.com.ingeneo.api.config.exception.CustomValidationException;
import co.com.ingeneo.api.config.exception.RecursoNoEncontrado;
import co.com.ingeneo.api.controller.request.ClienteRequest;
import co.com.ingeneo.api.controller.response.ClienteModel;
import co.com.ingeneo.api.controller.response.SelectOptionGeneric;
import co.com.ingeneo.api.repository.ClienteRepository;
import co.com.ingeneo.api.repository.domain.Cliente;
import co.com.ingeneo.api.service.ClienteService;
import co.com.ingeneo.api.service.GenericSpecificationGenerator;
import co.com.ingeneo.api.service.assembler.ClienteAssembler;
import co.com.ingeneo.api.service.assembler.SelectOptionAssembler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class ClienteServiceImpl extends GenericSpecificationGenerator<Cliente> implements ClienteService {
	
	@NonNull
	private final ClienteRepository clienteRepository;

	@NonNull
	private final ClienteAssembler clienteAssembler;

	@NonNull
	private final PagedResourcesAssembler<Cliente> pagedResourcesAssembler;

	@Transactional(readOnly = true)
	@Override
	public PagedModel<ClienteModel> getAllClienteFiltered(final Integer page, final Integer size, final String filter, final String sort) {
		Sort sortBy = CommonConfigurationUtils.generateSort(sort, this::replaceAlias);

		Pageable pageable = CommonConfigurationUtils.generatePageable(page, size, sortBy);

		return pagedResourcesAssembler.toModel(clienteRepository.findAll(generateSpecification(filter), pageable), clienteAssembler);
	}
	
	public String replaceAlias(String s) {
		if ("clienteId".equals(s)) {
			return "id";
		}
		return s;
	}

	@Override
	public ClienteModel getClienteById(final Long id) {
		Cliente cliente = fechClienteById(id);

		return clienteAssembler.toModel(cliente);
	}

	@Override
	public ClienteModel save(final ClienteRequest clienteRequest) {
		Cliente clienteSave = clienteAssembler.getClienteMapper().toCliente(clienteRequest);

		if (clienteRepository.countBycorreo(clienteRequest.getCorreo()) > 0) {
			throw new CustomValidationException(
					"El correo ya esta registrado en nuestra base, por favor utilizar otro");
		}

		clienteRepository.save(clienteSave);

		return clienteAssembler.toModel(clienteSave);
	}

	@Override
	public void update(final Long clienteId, final ClienteRequest clienteRequest) {
		fechClienteById(clienteId);

		Cliente clienteUpdate = clienteAssembler.getClienteMapper().toCliente(clienteRequest);

		clienteUpdate.setId(clienteId);

		clienteRepository.save(clienteUpdate);

	}

	@Override
	public void deleteById(final Long id) {
		clienteRepository.delete(fechClienteById(id));
	}

	@Transactional(readOnly = true)
	@Override
	public CollectionModel<SelectOptionGeneric> selectCliente(Class<?> controller) {
		return SelectOptionAssembler.toCollectionModelGeneric(clienteRepository.findAll(), 
				cliente -> new SelectOptionGeneric(cliente.getId().toString(), cliente.nombreCompleto()), 
				controller);
	}
	
	private Cliente fechClienteById(final Long id) {
		return clienteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontrado(
				"Cliente con identificador ".concat(id.toString().concat(" no encontrado"))));
	}

}