package co.com.ingeneo.api.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
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

import co.com.ingeneo.api.controller.request.DestinoEntregaRequest;
import co.com.ingeneo.api.controller.response.DestinoEntregaModel;
import co.com.ingeneo.api.controller.response.SelectOptionGeneric;
import co.com.ingeneo.api.service.DestinoEntregaService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/destinosEntregas")
public class DestinoEntregaController {

	
	private final @NonNull DestinoEntregaService destinoEntregaService;
	
	@GetMapping({"","/"})
	@ResponseStatus(HttpStatus.OK)
	public PagedModel<DestinoEntregaModel> getAll(@RequestParam(value = "page") final Integer page,
			@RequestParam(value = "size") final Integer size,
			@RequestParam(value = "filter",required = false) final String filter,
			@RequestParam(value = "sort",required = false) final String sort){
		return destinoEntregaService.getAll(page,size,filter,sort);
	}
	
	@GetMapping("/{destinoEntregaId}")
	@ResponseStatus(HttpStatus.OK)
	public DestinoEntregaModel getOne(@PathVariable Long destinoEntregaId){
		return destinoEntregaService.getDestinoEntregaById(destinoEntregaId) ;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
    public DestinoEntregaModel saveEntity(@RequestBody @Valid DestinoEntregaRequest destinoEntregaRequest) {
        return destinoEntregaService.save(destinoEntregaRequest);
    }
	
	@PutMapping("/{destinoEntregaId}")
	@ResponseStatus(HttpStatus.OK)
	public DestinoEntregaModel update(@PathVariable Long destinoEntregaId,@RequestBody @Validated DestinoEntregaRequest destinoEntregaRequest){
		return destinoEntregaService.update(destinoEntregaId, destinoEntregaRequest);
	}
	
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