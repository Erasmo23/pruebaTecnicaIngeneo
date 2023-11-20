package co.com.ingeneo.api.controller;

import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.com.ingeneo.api.config.SecurityUtils;
import co.com.ingeneo.api.controller.request.OrdenMaritimaRequest;
import co.com.ingeneo.api.controller.request.OrdenTerrestreRequest;
import co.com.ingeneo.api.controller.response.OrdenModel;
import co.com.ingeneo.api.service.OrdenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ordenes")
@Tag(name = "Controlador Ordenes", 
		description = "EndPoint's para realizar el CRUD de las Ordenes del sistema" )
public class OrdenController {
	
	private static final String HAS_AUTHORITY =  SecurityUtils.HAS_AUTHORITY_ORDER;
	
	private final @NonNull OrdenService ordenService;
	
	@PreAuthorize(HAS_AUTHORITY)
	@GetMapping({"","/"})
	public PagedModel<OrdenModel> getAll(@RequestParam(value = "page") final Integer page,
			@RequestParam(value = "size") final Integer size,
			@RequestParam(value = "filter",required = false) final String filter,
			@RequestParam(value = "sort",required = false) final String sort){
		return ordenService.getAllOrdenesFiltered(page,size,filter,sort);
	}
	
	@PreAuthorize(HAS_AUTHORITY)
	@GetMapping("/{ordenId}")
	public OrdenModel getOne(@PathVariable Long ordenId){
		return ordenService.getOrdenById(ordenId) ;
	}
	
	@GetMapping("/numeroGuia/{numeroGuia}")
	public OrdenModel getOrdenByNumeroGuia(@PathVariable String numeroGuia){
		return ordenService.getOrdenByNumGuia(numeroGuia) ;
	}
	
	@PreAuthorize(HAS_AUTHORITY)
	@PostMapping("/saveOrdenTerrestre")
	@ResponseStatus(HttpStatus.CREATED)
    public OrdenModel saveEntity(@RequestBody @Valid OrdenTerrestreRequest ordenTerrestreRequest) {
        return ordenService.saveOrdenTerrestre(ordenTerrestreRequest);
    }
	
	@PreAuthorize(HAS_AUTHORITY)
	@PostMapping("/saveOrdenMaritima")
	@ResponseStatus(HttpStatus.CREATED)
    public OrdenModel saveEntity(@RequestBody @Valid OrdenMaritimaRequest ordenMaritimaRequest) {
        return ordenService.saveOrdenMaritima(ordenMaritimaRequest);
    }
	
	@PreAuthorize(HAS_AUTHORITY)
	@PutMapping("/updateOrdenTerrestre/{ordenId}")
	public OrdenModel updateTerrestre(@PathVariable Long ordenId,@RequestBody @Validated OrdenTerrestreRequest ordenTerrestreRequest){
		return ordenService.updateOrdenTerrestre(ordenId, ordenTerrestreRequest);
	}
	
	@PreAuthorize(HAS_AUTHORITY)
	@PutMapping("/updateOrdenMaritima/{ordenId}")
	public OrdenModel updateMaritima(@PathVariable Long ordenId,@RequestBody @Validated OrdenMaritimaRequest ordenMaritimaRequest){
		return ordenService.updateOrdenMaritima(ordenId, ordenMaritimaRequest);
	}
	
	@PreAuthorize(HAS_AUTHORITY)
	@DeleteMapping("/{ordenId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long ordenId){
		ordenService.deleteById(ordenId);
	}
	
	@PreAuthorize(HAS_AUTHORITY)
	@PatchMapping("/{ordenId}/ordenEnviada")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ordenEnviada(@PathVariable Long ordenId){
		ordenService.ordenEnviada(ordenId);
	}
	
	@PreAuthorize(HAS_AUTHORITY)
	@PatchMapping("/{ordenId}/ordenFinalizada/")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ordenFinalizada(@PathVariable Long ordenId){
		ordenService.ordenFinalizada(ordenId);
	}
	
}