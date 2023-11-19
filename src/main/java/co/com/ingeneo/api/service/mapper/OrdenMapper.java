package co.com.ingeneo.api.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import co.com.ingeneo.api.controller.request.OrdenMaritimaRequest;
import co.com.ingeneo.api.controller.request.OrdenTerrestreRequest;
import co.com.ingeneo.api.controller.response.OrdenModel;
import co.com.ingeneo.api.repository.domain.Orden;

@Mapper(componentModel = "spring")
public interface OrdenMapper {

	OrdenMapper INSTANCE = Mappers.getMapper(OrdenMapper.class);

	@Mappings({ @Mapping(source = "clienteId", target = "cliente.id"),
			@Mapping(source = "productoId", target = "producto.id"),
			@Mapping(source = "destinoEntregaId", target = "destinoEntrega.id"),
			@Mapping(source = "precioEnvio", target = "precioEnvioOriginal"),
			@Mapping(source = "placaVehiculo", target = "identificacionTransporte"),
			@Mapping(ignore = true, target = "fechaRegistro"), @Mapping(ignore = true, target = "id"),
			@Mapping(ignore = true, target = "estadoOrden"),
			@Mapping(ignore = true, target = "precioEnvioDescuento"), @Mapping(ignore = true, target = "numeroGuia") })
	Orden toOrden(OrdenTerrestreRequest ordenTerresteRequest);

	@Mappings({ @Mapping(source = "clienteId", target = "cliente.id"),
			@Mapping(source = "productoId", target = "producto.id"),
			@Mapping(source = "destinoEntregaId", target = "destinoEntrega.id"),
			@Mapping(source = "precioEnvio", target = "precioEnvioOriginal"),
			@Mapping(source = "numeroFlota", target = "identificacionTransporte"),
			@Mapping(ignore = true, target = "fechaRegistro"), @Mapping(ignore = true, target = "id"),
			@Mapping(ignore = true, target = "estadoOrden"),
			@Mapping(ignore = true, target = "precioEnvioDescuento"), @Mapping(ignore = true, target = "numeroGuia") })
	Orden toOrden(OrdenMaritimaRequest ordenMaritimaRequest);

	@Mappings({ @Mapping(source = "id", target = "ordenId"),
			@Mapping(source = "producto.id", target = "producto.productoId"),
			@Mapping(source = "destinoEntrega.id", target = "destinoEntrega.destinoEntregaId"),
			@Mapping(source = "cliente.id", target = "cliente.clienteId"),
			@Mapping(source = "destinoEntrega.tipoTransporte.id", target = "destinoEntrega.tipoTransporte.tipoTransporteId"),
			@Mapping(source = "destinoEntrega.pais.id", target = "destinoEntrega.pais.paisId")
			})
	OrdenModel toOrdenModel(Orden orden);

}