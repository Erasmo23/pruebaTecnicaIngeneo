package co.com.ingeneo.api.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.com.ingeneo.api.repository.SecUsuarioRepository;
import co.com.ingeneo.api.repository.domain.SecUsuario;

@Service
public class JpaUserDetailsService implements UserDetailsService {
	
	private final SecUsuarioRepository secUsuarioRepository;
	
	@Autowired
	public JpaUserDetailsService(SecUsuarioRepository secUsuarioRepository) {
		this.secUsuarioRepository = secUsuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		SecUsuario secUsuario = secUsuarioRepository.findByCorreo(username).orElseThrow(
				() -> new UsernameNotFoundException("correo: " + username + " no registrado en el sistema.") 
		);
		
		Integer countRolesUsuarios = secUsuarioRepository.countRolesUsuario(secUsuario.getId());
		
		if (countRolesUsuarios == 0) {
			throw new UsernameNotFoundException("El usuario registrado no posee roles en el sistema, favor contactar al administrador");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		secUsuarioRepository.getRolesByUsuario(secUsuario).forEach(rol -> authorities.add(new SimpleGrantedAuthority(rol.getCodigo())));
		
		return new User(secUsuario.getCorreo(), secUsuario.getPassword(), secUsuario.getActivo(), true, true, true, authorities);
	}

}