package co.com.ingeneo.api.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import co.com.ingeneo.api.controller.request.DestinoEntregaRequest;
import co.com.ingeneo.api.controller.response.DestinoEntregaModel;
import co.com.ingeneo.api.repository.domain.DestinoEntrega;

@Mapper(componentModel = "spring")
public interface DestinoEntregaMapper {

	DestinoEntregaMapper INSTANCE = Mappers.getMapper(DestinoEntregaMapper.class);

	@Mappings({ @Mapping(source = "paisId", target = "pais.id"),
			@Mapping(source = "tipoTransporteId", target = "tipoTransporte.id"),
			@Mapping(ignore = true, target = "fechaRegistro"), @Mapping(ignore = true, target = "id"),
			@Mapping(ignore = true, target = "ordenes") })
	DestinoEntrega toDestinoEntrega(final DestinoEntregaRequest destinoEntregaRequest);

	@Mapping(source = "id", target = "destinoEntregaId")
	@Mapping(source ="pais.id" , target = "pais.paisId" )
	@Mapping(source ="tipoTransporte.id" , target = "tipoTransporte.tipoTransporteId" )
	DestinoEntregaModel toDestinoEntregaModel(final DestinoEntrega destinoEntrega);

}