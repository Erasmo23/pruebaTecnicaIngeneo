package co.com.ingeneo.api.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.com.ingeneo.api.config.SecurityUtils;
import co.com.ingeneo.api.controller.request.DestinoEntregaRequest;
import co.com.ingeneo.api.controller.response.DestinoEntregaModel;
import co.com.ingeneo.api.controller.response.SelectOptionGeneric;
import co.com.ingeneo.api.service.DestinoEntregaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/destinosEntregas")
@Tag(name = "Controlador Destinos Entregas", 
		description = "EndPoint's para realizar el CRUD de los distintas Bodegas de Almacenamientos y Puertos Maritimos que se manejan en el sistema" )
public class DestinoEntregaController {

	private static final String HAS_AUTHORITY =  SecurityUtils.HAS_AUTHORITY_REGISTER;
	
	private final @NonNull DestinoEntregaService destinoEntregaService;
	
	@PreAuthorize(HAS_AUTHORITY)
	@GetMapping({"","/"})
	@ResponseStatus(HttpStatus.OK)
	public PagedModel<DestinoEntregaModel> getAll(@RequestParam(value = "page") final Integer page,
			@RequestParam(value = "size") final Integer size,
			@RequestParam(value = "filter",required = false) final String filter,
			@RequestParam(value = "sort",required = false) final String sort){
		return destinoEntregaService.getAll(page,size,filter,sort);
	}
	
	@PreAuthorize(HAS_AUTHORITY)
	@GetMapping("/{destinoEntregaId}")
	@ResponseStatus(HttpStatus.OK)
	public DestinoEntregaModel getOne(@PathVariable Long destinoEntregaId){
		return destinoEntregaService.getDestinoEntregaById(destinoEntregaId) ;
	}
	
	@PreAuthorize(HAS_AUTHORITY)
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
    public DestinoEntregaModel saveEntity(@RequestBody @Valid DestinoEntregaRequest destinoEntregaRequest) {
        return destinoEntregaService.save(destinoEntregaRequest);
    }
	
	@PreAuthorize(HAS_AUTHORITY)
	@PutMapping("/{destinoEntregaId}")
	@ResponseStatus(HttpStatus.OK)
	public DestinoEntregaModel update(@PathVariable Long destinoEntregaId,@RequestBody @Validated DestinoEntregaRequest destinoEntregaRequest){
		return destinoEntregaService.update(destinoEntregaId, destinoEntregaRequest);
	}
	
	@PreAuthorize(HAS_AUTHORITY)
	@DeleteMapping("/{destinoEntregaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long destinoEntregaId){
		destinoEntregaService.deleteById(destinoEntregaId);
	}
	
	@GetMapping("/select")
	@ResponseStatus(HttpStatus.OK)
	public CollectionModel<SelectOptionGeneric> selectOptionGeneric(){
		return destinoEntregaService.selectDestinoEntrega(getClass());
	}
	
}