package co.com.ingeneo.api.service;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import co.com.ingeneo.api.controller.request.DestinoEntregaRequest;
import co.com.ingeneo.api.controller.response.DestinoEntregaModel;
import co.com.ingeneo.api.controller.response.SelectOptionGeneric;

public interface DestinoEntregaService {

	PagedModel<DestinoEntregaModel> getAll(final Integer page, final Integer size, String filter, String sort);
	
	DestinoEntregaModel getDestinoEntregaById(final Long id);
	
	DestinoEntregaModel save(final DestinoEntregaRequest destinoEntregaRequest);
	
	DestinoEntregaModel update(final Long destinoEntregaId,final DestinoEntregaRequest destinoEntregaRequest);
	
	void deleteById(final Long id);

	CollectionModel<SelectOptionGeneric> selectDestinoEntrega(Class<?> controller);
	
}