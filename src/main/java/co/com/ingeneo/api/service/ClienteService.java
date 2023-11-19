package co.com.ingeneo.api.service;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import co.com.ingeneo.api.controller.request.ClienteRequest;
import co.com.ingeneo.api.controller.response.ClienteModel;
import co.com.ingeneo.api.controller.response.SelectOptionGeneric;

public interface ClienteService {

	PagedModel<ClienteModel> getAllClienteFiltered(final Integer page, final Integer size, final String filter, final String sort);

	ClienteModel getClienteById(final Long id);
	
	ClienteModel save(final ClienteRequest clienteRequest);
	
	ClienteModel update(final Long clienteId,final ClienteRequest clienteRequest);
	
	void deleteById(final Long id);

	CollectionModel<SelectOptionGeneric> selectCliente(final Class<?> controller);
	
}