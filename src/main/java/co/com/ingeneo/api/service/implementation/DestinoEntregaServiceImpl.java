package co.com.ingeneo.api.service.implementation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.ingeneo.api.config.PageableUtils;
import co.com.ingeneo.api.config.exception.CustomValidationException;
import co.com.ingeneo.api.config.exception.RecursoNoEncontrado;
import co.com.ingeneo.api.controller.request.DestinoEntregaRequest;
import co.com.ingeneo.api.controller.response.DestinoEntregaModel;
import co.com.ingeneo.api.controller.response.SelectOptionGeneric;
import co.com.ingeneo.api.repository.DestinoEntregaRepository;
import co.com.ingeneo.api.repository.PaisRepository;
import co.com.ingeneo.api.repository.TipoTransporteRepository;
import co.com.ingeneo.api.repository.domain.DestinoEntrega;
import co.com.ingeneo.api.repository.domain.Pais;
import co.com.ingeneo.api.repository.domain.TipoTransporte;
import co.com.ingeneo.api.service.DestinoEntregaService;
import co.com.ingeneo.api.service.GenericSpecificationGenerator;
import co.com.ingeneo.api.service.assembler.DestinoEntregaAssembler;
import co.com.ingeneo.api.service.assembler.SelectOptionAssembler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DestinoEntregaServiceImpl extends GenericSpecificationGenerator<DestinoEntrega> implements DestinoEntregaService {
	
	@NonNull
	private final DestinoEntregaRepository destinoEntregaRepository;
	
	@NonNull
	private final PaisRepository paisRepository;
	
	@NonNull
	private final TipoTransporteRepository tipoTransporteRepository;
	
	@NonNull
	private final DestinoEntregaAssembler destinoEntregaAssembler;
	
	@NonNull
	private final PagedResourcesAssembler<DestinoEntrega> pagedResourcesAssembler;

	@Transactional(readOnly = true)
	@Override
	public PagedModel<DestinoEntregaModel> getAll(final Integer page, final Integer size, String filter, String sort) {
		
		Sort sortBy = PageableUtils.generateSort(sort, this::replaceAlias);

		Pageable pageable = PageableUtils.generatePageable(page, size, sortBy);
		
		return pagedResourcesAssembler.toModel(destinoEntregaRepository.findAll(generateSpecification(filter) , pageable), destinoEntregaAssembler);
	}
	
	@Override
	protected String replaceAlias(String s) {
		if ("destinoEntregaId".equals(s)) {
			return "id";
		}else if ("paisId".equals(s)) {
			return "pais.id";
		}else if ("tipoTransporteId".equals(s)) {
			return "tipoTransporte.id";
		}
		return s;
	}

	@Transactional(readOnly = true)
	@Override
	public DestinoEntregaModel getDestinoEntregaById(Long id) {
		return destinoEntregaAssembler.toModel(fechDestinoEntregaById(id));
	}

	@Override
	public DestinoEntregaModel save(DestinoEntregaRequest destinoEntregaRequest) {
		
		Pais paisRelation = paisRepository.findById(destinoEntregaRequest.getPaisId()).orElseThrow(() -> 
			new CustomValidationException("El Pais con identificador ".concat(destinoEntregaRequest.getPaisId().toString()).concat(" no esta registrado en el sistema")
		));
		
		TipoTransporte tipoTransporteRelation = tipoTransporteRepository.findById(destinoEntregaRequest.getTipoTransporteId()).orElseThrow(
				() -> new CustomValidationException("El Tipo de Transporte con identificador ".concat(destinoEntregaRequest.getTipoTransporteId().toString()).concat(" no esta registrado en el sistema")
		));
		
		DestinoEntrega destinoEntregaSave = destinoEntregaAssembler.getDestinoEntregaMapper().toDestinoEntrega(destinoEntregaRequest);
		
		destinoEntregaSave.setPais(paisRelation);
		destinoEntregaSave.setTipoTransporte(tipoTransporteRelation);
		
		destinoEntregaRepository.save(destinoEntregaSave);
		
		return destinoEntregaAssembler.toModel(destinoEntregaSave);
	}
	
	@Override
	public DestinoEntregaModel update(Long destinoEntregaId, DestinoEntregaRequest destinoEntregaRequest) {
		fechDestinoEntregaById(destinoEntregaId);
		
		Pais paisRelation = paisRepository.findById(destinoEntregaRequest.getPaisId()).orElseThrow(() -> 
			new CustomValidationException("El Pais con identificador ".concat(destinoEntregaRequest.getPaisId().toString()).concat(" no esta registrado en el sistema")
		));
		
		TipoTransporte tipoTransporteRelation = tipoTransporteRepository.findById(destinoEntregaRequest.getTipoTransporteId()).orElseThrow(
				() -> new CustomValidationException("El Tipo de Transporte con identificador ".concat(destinoEntregaRequest.getTipoTransporteId().toString()).concat(" no esta registrado en el sistema")
		));
		
		DestinoEntrega destinoEntregaUpdate = destinoEntregaAssembler.getDestinoEntregaMapper().toDestinoEntrega(destinoEntregaRequest);
		
		destinoEntregaUpdate.setPais(paisRelation);
		destinoEntregaUpdate.setTipoTransporte(tipoTransporteRelation);
		
		destinoEntregaUpdate.setId(destinoEntregaId);
		
		destinoEntregaRepository.save(destinoEntregaUpdate);
		
		return destinoEntregaAssembler.toModel(destinoEntregaUpdate);
		
	}

	@Override
	public void deleteById(Long id) {
		DestinoEntrega destinoEntrega = fechDestinoEntregaById(id);
		destinoEntregaRepository.delete(destinoEntrega);
	}
	
	public DestinoEntrega fechDestinoEntregaById(Long id) {
		return destinoEntregaRepository.findById(id).orElseThrow(
				() -> new RecursoNoEncontrado("DestinoEntrega con identificador ".concat(id.toString().concat(" no encontrado"))
				));
				
	}

	@Transactional(readOnly = true)
	@Override
	public CollectionModel<SelectOptionGeneric> selectDestinoEntrega(Class<?> controller) {
		return SelectOptionAssembler.toCollectionModelGeneric(destinoEntregaRepository.findAll(), 
				destinoEntrega -> new SelectOptionGeneric(destinoEntrega.getId().toString(), destinoEntrega.getNombre()), 
				controller);
	}

}