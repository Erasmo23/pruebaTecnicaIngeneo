package co.com.ingeneo.api.service.implementation;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.ingeneo.api.config.CommonConfigurationUtils;
import co.com.ingeneo.api.config.PageableUtils;
import co.com.ingeneo.api.config.exception.CustomValidationException;
import co.com.ingeneo.api.config.exception.RecursoNoEncontrado;
import co.com.ingeneo.api.controller.request.OrdenMaritimaRequest;
import co.com.ingeneo.api.controller.request.OrdenTerrestreRequest;
import co.com.ingeneo.api.controller.response.OrdenModel;
import co.com.ingeneo.api.repository.ClienteRepository;
import co.com.ingeneo.api.repository.DestinoEntregaRepository;
import co.com.ingeneo.api.repository.EstadoOrdenRepository;
import co.com.ingeneo.api.repository.OrdenRepository;
import co.com.ingeneo.api.repository.ProductoRepository;
import co.com.ingeneo.api.repository.domain.Cliente;
import co.com.ingeneo.api.repository.domain.DestinoEntrega;
import co.com.ingeneo.api.repository.domain.EstadoOrden;
import co.com.ingeneo.api.repository.domain.Orden;
import co.com.ingeneo.api.repository.domain.Producto;
import co.com.ingeneo.api.service.GenericSpecificationGenerator;
import co.com.ingeneo.api.service.OrdenService;
import co.com.ingeneo.api.service.assembler.OrdenAssembler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrdenServiceImpl extends GenericSpecificationGenerator<Orden> implements OrdenService {

	@NonNull
	private final OrdenRepository ordenRepository;

	@NonNull
	private final EstadoOrdenRepository estadoOrdenRepository;

	@NonNull
	private final ClienteRepository clienteRepository;

	@NonNull
	private final ProductoRepository productoRepository;

	@NonNull
	private final DestinoEntregaRepository destinoEntregaRepository;

	@NonNull
	private final OrdenAssembler ordenAssembler;

	@NonNull
	private final PagedResourcesAssembler<Orden> pagedResourcesAssembler;

	@Transactional(readOnly = true)
	@Override
	public PagedModel<OrdenModel> getAllOrdenesFiltered(final Integer page, final Integer size, final String filter, final String sort) {

		Sort sortBy = PageableUtils.generateSort(sort, this::replaceAlias);

		Pageable pageable = PageableUtils.generatePageable(page, size, sortBy);

		return pagedResourcesAssembler.toModel(ordenRepository.findAll(generateSpecification(filter), pageable),
				ordenAssembler);
	}

	@Override
	protected String replaceAlias(String s) {
		if ("ordenId".equals(s)) {
			return "id";
		}else if ("clienteId".equals(s)) {
			return "cliente.id";
		}else if ("productoId".equals(s)) {
			return "producto.id";
		}else if ("destinoEntregaId".equals(s)) {
			return "destinoEntrega.id";
		}else if ("paisId".equals(s)) {
			return "destinoEntrega.pais.id";
		}else if ("tipoTransporteId".equals(s)) {
			return "destinoEntrega.tipoTransporte.id";
		}
		return s;
	}

	@Transactional(readOnly = true)
	@Override
	public OrdenModel getOrdenById(final Long ordenId) {
		return ordenAssembler.toModel(fechOrdenById(ordenId));
	}

	@Override
	public OrdenModel saveOrdenMaritima(final OrdenMaritimaRequest ordenMaritimaRequest) {
		Orden ordenSave = getOrdenGeneric(null,ordenAssembler.getOrdenMapper().toOrden(ordenMaritimaRequest), 
				ordenMaritimaRequest.getClienteId(), ordenMaritimaRequest.getProductoId(), 
				ordenMaritimaRequest.getDestinoEntregaId(), new BigDecimal("0.97"));

		return ordenAssembler.toModel(ordenRepository.save(ordenSave));
	}

	@Override
	public OrdenModel saveOrdenTerrestre(final OrdenTerrestreRequest ordenTerrestreRequest) {

		Orden ordenSave = getOrdenGeneric(null, ordenAssembler.getOrdenMapper().toOrden(ordenTerrestreRequest), 
				ordenTerrestreRequest.getClienteId(), ordenTerrestreRequest.getProductoId(), 
				ordenTerrestreRequest.getDestinoEntregaId(), new BigDecimal("0.95"));
		
		return ordenAssembler.toModel(ordenRepository.save(ordenSave));
	}
	
	@Override
	public OrdenModel updateOrdenMaritima(final Long ordenId, final OrdenMaritimaRequest ordenMaritimaRequest) {
		
		Orden ordenUpdate = getOrdenGeneric(ordenId, ordenAssembler.getOrdenMapper().toOrden(ordenMaritimaRequest), 
				ordenMaritimaRequest.getClienteId(), ordenMaritimaRequest.getProductoId(), 
				ordenMaritimaRequest.getDestinoEntregaId(), new BigDecimal("0.97"));
		

		return ordenAssembler.toModel(ordenRepository.save(ordenUpdate));
	}

	@Override
	public OrdenModel updateOrdenTerrestre(final Long ordenId, final OrdenTerrestreRequest ordenTerrestreRequest) {	
		Orden ordenUpdate = getOrdenGeneric(ordenId, ordenAssembler.getOrdenMapper().toOrden(ordenTerrestreRequest), 
				ordenTerrestreRequest.getClienteId(), ordenTerrestreRequest.getProductoId(), 
				ordenTerrestreRequest.getDestinoEntregaId(), new BigDecimal("0.95"));
		return ordenAssembler.toModel(ordenRepository.save(ordenUpdate));
	}
	
	/**
	 * Metodo que realiza el guardado o edicion de la Orden dependiendo de los datos enviados
	 * @param ordenId Si es nuevo registro se manda null, sino lo que viene del request
	 * @param orden Entidad ya transforma ya sea de un OrdenMaritimaRequest o OrdenTerrestreRequest
	 * @param clienteId Relacion de la Orden, se valida que sea un dato valido que exista en la base
	 * @param productoId Relacion de la Orden, se valida que sea un dato valido que exista en la base
	 * @param destinoEntregaId Relacion de la Orden, se valida que sea un dato valido que exista en la base
	 * @param multiplicadorDescuento Si la cantidad del producto es mayor a 10 se aplicara este descuento enviado, ejemplo si es 5% se deberia de enviar 0.95
	 * @return
	 */
	private Orden getOrdenGeneric(final Long ordenId, Orden orden, final Long clienteId, final Long productoId, 
			final Long destinoEntregaId, final BigDecimal multiplicadorDescuento) {

		EstadoOrden estadoOrden;
		String numeroGuia;
		
		if(ordenId != null) {
			Orden ordenBd = fechOrdenById(ordenId);
			
			estadoOrden = ordenRepository.findEstadoOrdenByOrdenId(ordenBd);
			if (!"CRE".equals(estadoOrden.getCodigo())) {
				throw new CustomValidationException("Una orden no puede eliminarse si ya cambio de estado de creada.");
			}
			
			numeroGuia = ordenBd.getNumeroGuia();
		}else {
			estadoOrden = estadoOrdenRepository.findByCodigo("CRE");
			numeroGuia = CommonConfigurationUtils.generarStringAlfanumericoAleatorio(10);
		}
		
		
		Cliente clienteRelation = getCliente(clienteId);

		Producto productoRelation = getProducto(productoId);
		
		DestinoEntrega destinoEntregaRelation = getDestinoEntrega(destinoEntregaId);
		
		orden.setId(ordenId);
		orden.setCliente(clienteRelation);
		orden.setProducto(productoRelation);
		orden.setDestinoEntrega(destinoEntregaRelation);
		orden.setEstadoOrden(estadoOrden);
		orden.setNumeroGuia(numeroGuia);
		
		if (orden.getCantidadProducto() > 10) {
			orden.setPrecioEnvioDescuento(orden.getPrecioEnvioOriginal().multiply(multiplicadorDescuento).setScale(2, RoundingMode.HALF_UP));
		} else {
			orden.setPrecioEnvioDescuento(orden.getPrecioEnvioOriginal());
		}
		ordenRepository.save(orden);
		return orden;
	}
	
	private Cliente getCliente(final Long clientId) {
		return clienteRepository.findById(clientId)
		.orElseThrow(() -> new CustomValidationException(
				"El Cliente con identificador ".concat(clientId.toString())
						.concat(" no esta registrado en el sistema")));
	}
	
	private Producto getProducto(final Long productoId) {
		return productoRepository.findById(productoId)
				.orElseThrow(() -> new CustomValidationException(
						"El Producto con identificador ".concat(productoId.toString())
								.concat(" no esta registrado en el sistema")));
	}
	
	private DestinoEntrega getDestinoEntrega(final Long destinoEntregaId) {
		return destinoEntregaRepository
				.findById(destinoEntregaId)
				.orElseThrow(() -> new CustomValidationException("El Destino Entrega con identificador "
						.concat(destinoEntregaId.toString())
						.concat(" no esta registrado en el sistema")));
	}

	@Override
	public void deleteById(final Long ordenId) {
		Orden orden = fechOrdenById(ordenId);
		EstadoOrden estadoOrden = ordenRepository.findEstadoOrdenByOrdenId(orden);
		if (!"CRE".equals(estadoOrden.getCodigo())) {
			throw new CustomValidationException("Una orden no puede eliminarse si ya cambio de estado de creada.");
		}
		ordenRepository.delete(orden);
	}

	public Orden fechOrdenById(final Long id) {
		return ordenRepository.findById(id).orElseThrow(() -> new RecursoNoEncontrado(
				"Orden con identificador ".concat(id.toString().concat(" no encontrado"))));

	}

	@Override
	public void ordenEnviada(final Long ordenId) {
		fechOrdenById(ordenId);
		ordenRepository.editEstadoOrden(estadoOrdenRepository.findByCodigo("ENCAM"), ordenId);
	}

	@Override
	public void ordenFinalizada(final Long ordenId) {
		fechOrdenById(ordenId);
		ordenRepository.editEstadoOrden(estadoOrdenRepository.findByCodigo("ENTRE"), ordenId);
	}

	@Transactional(readOnly = true)
	@Override
	public OrdenModel getOrdenByNumGuia(final String numeroGuia) {
		
		Orden orden = ordenRepository.findByNumeroGuia(numeroGuia).orElseThrow(
				()-> new RecursoNoEncontrado("No se encontro la orden con ese numero de guia."));
		
		return ordenAssembler.toModel(orden);
	}

}