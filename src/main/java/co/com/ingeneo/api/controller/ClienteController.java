package co.com.ingeneo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import co.com.ingeneo.api.controller.request.ClienteRequest;
import co.com.ingeneo.api.controller.response.ClienteModel;
import co.com.ingeneo.api.controller.response.SelectOptionGeneric;
import co.com.ingeneo.api.service.ClienteService;
import jakarta.validation.Valid;
import lombok.NonNull;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

	private final @NonNull ClienteService clienteService;
	
	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@GetMapping({"/",""})
	@ResponseStatus(HttpStatus.OK)
	public PagedModel<ClienteModel> filtered(@RequestParam(value = "page") final Integer page,
			@RequestParam(value = "size") final Integer size,
			@RequestParam(value = "filter",required = false) final String filter,
			@RequestParam(value = "sort",required = false) final String sort){
		return clienteService.getAllClienteFiltered(page, size, filter, sort);
	}
	
	@GetMapping("/{clienteId}")
	@ResponseStatus(HttpStatus.OK)
	public ClienteModel getOne(@PathVariable final Long clienteId){
		return clienteService.getClienteById(clienteId) ;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
    public ClienteModel saveEntity(@RequestBody @Valid final ClienteRequest clienteRequest) {
        return clienteService.save(clienteRequest);
    }
	
	@PutMapping("/{clienteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long clienteId,@RequestBody @Validated final ClienteRequest clienteRequest){
		clienteService.update(clienteId, clienteRequest);
	}
	
	@DeleteMapping("/{clienteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable final Long clienteId){
		clienteService.deleteById(clienteId);
	}
	
	@GetMapping("/select")
	@ResponseStatus(HttpStatus.OK)
	public CollectionModel<SelectOptionGeneric> selectOptionGeneric(){
		return clienteService.selectCliente(getClass());
	}
	
}