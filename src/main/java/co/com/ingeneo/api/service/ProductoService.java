package co.com.ingeneo.api.service;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import co.com.ingeneo.api.controller.request.ProductoRequest;
import co.com.ingeneo.api.controller.response.ProductoModel;
import co.com.ingeneo.api.controller.response.SelectOptionGeneric;

public interface ProductoService {
	
	PagedModel<ProductoModel> getAllProductoFiltered(final Integer page, final Integer size, String filter, String sort);

	ProductoModel getProductoById(final Long id);
	
	ProductoModel save(final ProductoRequest productoRequest);
	
	ProductoModel update(final Long productoId,final ProductoRequest productoRequest);
	
	void deleteById(final Long id);

	CollectionModel<SelectOptionGeneric> selectProducto(final Class<?> controller);
	
}