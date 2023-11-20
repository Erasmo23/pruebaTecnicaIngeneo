package co.com.ingeneo.api.service.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.Optional;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import co.com.ingeneo.api.controller.ProductoController;
import co.com.ingeneo.api.controller.response.ProductoModel;
import co.com.ingeneo.api.repository.domain.Producto;
import co.com.ingeneo.api.service.mapper.ProductoMapper;
import lombok.Getter;
import lombok.NonNull;

@Component
public class ProductoAssembler extends RepresentationModelAssemblerSupport<Producto, ProductoModel>  {

	@Getter
	private final @NonNull ProductoMapper productoMapper;
	
	
	public ProductoAssembler(final @NonNull ProductoMapper productoMapper) {
		super(ProductoController.class, ProductoModel.class);
		this.productoMapper = productoMapper;
	}
	
	@Override
	public ProductoModel toModel(Producto entity) {
		return Optional.ofNullable(entity).map(value -> {
			ProductoModel model = this.productoMapper.toProductoModel(value);
			model.add(linkTo(getControllerClass()).slash(model.getProductoId()).withSelfRel());
			return model;
		}).orElse(null);
	}

}