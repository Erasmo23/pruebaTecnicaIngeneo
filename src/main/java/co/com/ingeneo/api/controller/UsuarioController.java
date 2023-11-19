package co.com.ingeneo.api.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.com.ingeneo.api.config.SecurityUtils;
import co.com.ingeneo.api.controller.request.UsuarioRolRequest;
import co.com.ingeneo.api.controller.response.SelectOptionGeneric;
import co.com.ingeneo.api.service.SecUsuarioService;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

	private static final String HAS_AUTHORITY =  SecurityUtils.HAS_AUTHORITY_ADMIN;
	
	@NonNull
	private final SecUsuarioService secUsuarioService;
	
	@PreAuthorize(HAS_AUTHORITY)
	@DeleteMapping("/usuarioRol/{idUsuarioRol}/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long idUsuarioRol){
		secUsuarioService.deleteUsuarioRol(idUsuarioRol);
	}
	
	@PreAuthorize(HAS_AUTHORITY)
	@PostMapping("/usuarioRol/add")
	@ResponseStatus(HttpStatus.CREATED)
	public void createUsuarioRol(@RequestBody @Valid UsuarioRolRequest usuarioRolRequest) {
		secUsuarioService.addUsuarioRol(usuarioRolRequest);
	}
	
	@PreAuthorize(HAS_AUTHORITY)
	@GetMapping("/select")
	@ResponseStatus(HttpStatus.OK)
	public CollectionModel<SelectOptionGeneric> selectOptionGeneric(){
		return secUsuarioService.selectUsuarios(getClass());
	}
	
	@PreAuthorize(HAS_AUTHORITY)
	@GetMapping("/selectRoles")
	@ResponseStatus(HttpStatus.OK)
	public CollectionModel<SelectOptionGeneric> selectRoles(){
		return secUsuarioService.selecRoles(getClass(), "selectRoles");
	}
	
}