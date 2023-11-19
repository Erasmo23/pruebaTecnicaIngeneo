package co.com.ingeneo.api.service.implementation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.ingeneo.api.config.PageableUtils;
import co.com.ingeneo.api.config.exception.RecursoNoEncontrado;
import co.com.ingeneo.api.controller.request.ProductoRequest;
import co.com.ingeneo.api.controller.response.ProductoModel;
import co.com.ingeneo.api.controller.response.SelectOptionGeneric;
import co.com.ingeneo.api.repository.ProductoRepository;
import co.com.ingeneo.api.repository.domain.Producto;
import co.com.ingeneo.api.service.GenericSpecificationGenerator;
import co.com.ingeneo.api.service.ProductoService;
import co.com.ingeneo.api.service.assembler.ProductoAssembler;
import co.com.ingeneo.api.service.assembler.SelectOptionAssembler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductoServiceImpl extends GenericSpecificationGenerator<Producto> implements ProductoService {

	private final @NonNull ProductoRepository productoRepository;
	
	@NonNull
	private final ProductoAssembler productoAssembler;

	@NonNull
	private final PagedResourcesAssembler<Producto> pagedResourcesAssembler;
	
	@Transactional(readOnly = true)
	@Override
	public PagedModel<ProductoModel> getAllProductoFiltered(Integer page, Integer size, String filter,String sort) {
		
		Sort sortBy = PageableUtils.generateSort(sort, s -> replaceAlias(s));
		
		Pageable pageable = PageableUtils.generatePageable(page, size, sortBy);

		return pagedResourcesAssembler.toModel(productoRepository.findAll(generateSpecification(filter), pageable), productoAssembler);
	}

	protected String replaceAlias(String s) {
		if ("productoId".equals(s)) {
			return "id";
		}
		return s;
	}
	
	@Transactional(readOnly = true)
	@Override
	public ProductoModel getProductoById(final Long id) {
		return productoAssembler.toModel(fechProductoById(id));
	}

	@Override
	public ProductoModel save(final ProductoRequest productoRequest) {
		
		Producto productoSave = productoAssembler.getProductoMapper().toProducto(productoRequest);
		
		productoRepository.save(productoSave);
		
		return productoAssembler.toModel(productoSave);
	}

	@Override
	public ProductoModel update(final Long productoId, final ProductoRequest productoRequest) {
		fechProductoById(productoId);
		
		Producto productoUpdate = productoAssembler.getProductoMapper().toProducto(productoRequest);
		
		productoUpdate.setId(productoId);
		
		productoRepository.save(productoUpdate);
		
		return productoAssembler.toModel(productoUpdate);
		
	}

	@Override
	public void deleteById(final Long id) {
		Producto productoDelete = fechProductoById(id);
		productoRepository.delete(productoDelete);
	}

	private Producto fechProductoById(final Long id) {
		return productoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontrado(
				"Producto con identificador ".concat(id.toString().concat(" no encontrado"))));
	}

	@Transactional(readOnly = true)
	@Override
	public CollectionModel<SelectOptionGeneric> selectProducto(Class<?> controller) {
		return SelectOptionAssembler.toCollectionModelGeneric(productoRepository.findAll(), 
				producto -> new SelectOptionGeneric(producto.getId().toString(), producto.getNombre()), 
				controller);
	}

}