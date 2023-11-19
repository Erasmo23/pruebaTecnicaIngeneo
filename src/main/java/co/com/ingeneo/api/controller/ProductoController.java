package co.com.ingeneo.api.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
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

import co.com.ingeneo.api.controller.request.ProductoRequest;
import co.com.ingeneo.api.controller.response.ProductoModel;
import co.com.ingeneo.api.controller.response.SelectOptionGeneric;
import co.com.ingeneo.api.service.ProductoService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
	
	
	private final @NonNull ProductoService productoService;
	
	@GetMapping({"","/"})
	@ResponseStatus(HttpStatus.OK)
	public PagedModel<ProductoModel> getAll(@RequestParam(value = "page") final Integer page,
			@RequestParam(value = "size") final Integer size,
			@RequestParam(value = "filter",required = false) final String filter,
			@RequestParam(value = "sort",required = false) final String sort){
		return productoService.getAllProductoFiltered(page, size, filter, sort);
	}
	
	@GetMapping("/{productoId}")
	@ResponseStatus(HttpStatus.OK)
	public ProductoModel getOne(@PathVariable Long productoId){
		return productoService.getProductoById(productoId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
    public ProductoModel saveEntity(@RequestBody @Valid ProductoRequest productoRequest) {
        return productoService.save(productoRequest);
    }
	
	@PutMapping("/{productoId}")
	@ResponseStatus(HttpStatus.OK)
	public ProductoModel update(@PathVariable Long productoId,@RequestBody @Valid ProductoRequest productoRequest){
		return productoService.update(productoId, productoRequest);
	}
	
	@DeleteMapping("/{productoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long productoId){
		productoService.deleteById(productoId);
	}
	
	@GetMapping("/select")
	@ResponseStatus(HttpStatus.OK)
	public CollectionModel<SelectOptionGeneric> selectOptionGeneric(){
		return productoService.selectProducto(getClass());
	}
	
}