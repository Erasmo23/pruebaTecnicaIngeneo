package co.com.ingeneo.api.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import co.com.ingeneo.api.controller.request.ProductoRequest;
import co.com.ingeneo.api.controller.response.ProductoModel;
import co.com.ingeneo.api.repository.domain.Producto;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

	ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

	@Mappings({ @Mapping(ignore = true, target = "fechaRegistro"), @Mapping(ignore = true, target = "id"),
			@Mapping(ignore = true, target = "ordenes") })
	Producto toProducto(final ProductoRequest productoRequest);

	@Mapping(source = "id", target = "productoId")
	ProductoModel toProductoModel(final Producto producto);

}