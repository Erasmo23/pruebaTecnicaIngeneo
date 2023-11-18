package co.com.ingeneo.api.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import co.com.ingeneo.api.controller.request.ClienteRequest;
import co.com.ingeneo.api.controller.response.ClienteModel;
import co.com.ingeneo.api.repository.domain.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

	ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

	@Mapping(ignore = true, target = "fechaRegistro")
	@Mapping(ignore = true, target = "id")
	Cliente toCliente(final ClienteRequest clienteRequest);

	@Mapping(source = "id", target = "clienteId")
	ClienteModel toClienteModel(final Cliente cliente);
}