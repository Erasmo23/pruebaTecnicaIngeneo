package co.com.ingeneo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.ingeneo.api.repository.domain.SecUsuario;

public interface SecUsuarioRepository extends JpaRepository<SecUsuario, Long> {
	
}