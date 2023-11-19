package co.com.ingeneo.api.controller;

import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
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

import co.com.ingeneo.api.controller.request.OrdenMaritimaRequest;
import co.com.ingeneo.api.controller.request.OrdenTerrestreRequest;
import co.com.ingeneo.api.controller.response.OrdenModel;
import co.com.ingeneo.api.service.OrdenService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ordenes")
public class OrdenController {
	
	private final @NonNull OrdenService ordenService;
	
	@GetMapping({"","/"})
	public PagedModel<OrdenModel> getAll(@RequestParam(value = "page") final Integer page,
			@RequestParam(value = "size") final Integer size,
			@RequestParam(value = "filter",required = false) final String filter,
			@RequestParam(value = "sort",required = false) final String sort){
		return ordenService.getAllOrdenesFiltered(page,size,filter,sort);
	}
	
	@GetMapping("/{ordenId}")
	public OrdenModel getOne(@PathVariable Long ordenId){
		return ordenService.getOrdenById(ordenId) ;
	}
	
	@GetMapping("/numeroGuia/{numeroGuia}")
	public OrdenModel getOrdenByNumeroGuia(@PathVariable String numeroGuia){
		return ordenService.getOrdenByNumGuia(numeroGuia) ;
	}
	
	@PostMapping("/saveOrdenTerrestre")
	@ResponseStatus(HttpStatus.CREATED)
    public OrdenModel saveEntity(@RequestBody @Valid OrdenTerrestreRequest ordenTerrestreRequest) {
        return ordenService.saveOrdenTerrestre(ordenTerrestreRequest);
    }
	
	@PostMapping("/saveOrdenMaritima")
	@ResponseStatus(HttpStatus.CREATED)
    public OrdenModel saveEntity(@RequestBody @Valid OrdenMaritimaRequest ordenMaritimaRequest) {
        return ordenService.saveOrdenMaritima(ordenMaritimaRequest);
    }
	
	@PutMapping("/updateOrdenTerrestre/{ordenId}")
	public OrdenModel updateTerrestre(@PathVariable Long ordenId,@RequestBody @Validated OrdenTerrestreRequest ordenTerrestreRequest){
		return ordenService.updateOrdenTerrestre(ordenId, ordenTerrestreRequest);
	}
	
	@PutMapping("/updateOrdenMaritima/{ordenId}")
	public OrdenModel updateMaritima(@PathVariable Long ordenId,@RequestBody @Validated OrdenMaritimaRequest ordenMaritimaRequest){
		return ordenService.updateOrdenMaritima(ordenId, ordenMaritimaRequest);
	}
	
	@DeleteMapping("/{ordenId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long ordenId){
		ordenService.deleteById(ordenId);
	}
	
	@PatchMapping("/{ordenId}/ordenEnviada")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ordenEnviada(@PathVariable Long ordenId){
		ordenService.ordenEnviada(ordenId);
	}
	
	@PatchMapping("/{ordenId}/ordenFinalizada/")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ordenFinalizada(@PathVariable Long ordenId){
		ordenService.ordenFinalizada(ordenId);
	}
	
}