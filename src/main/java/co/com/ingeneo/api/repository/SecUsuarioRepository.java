package co.com.ingeneo.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.ingeneo.api.repository.domain.SecRol;
import co.com.ingeneo.api.repository.domain.SecUsuario;

public interface SecUsuarioRepository extends JpaRepository<SecUsuario, Long> {
	
	Optional<SecUsuario> findByCorreo(String correo);
	
	@Query("SELECT COUNT(0) FROM SecUsuarioRol x WHERE x.secUsuario.id = :idUsuario")
	Integer countRolesUsuario(@Param ("idUsuario") Long idUsuario);
	
	@Query("SELECT x.secRol FROM SecUsuarioRol x WHERE x.secUsuario =:usuario")
	List<SecRol> getRolesByUsuario(@Param ("usuario") SecUsuario secUsuario);

	@Query("SELECT COUNT(0) FROM SecUsuario u where u.correo=:correo")
	Integer countByCorreo(@Param ("correo") String correo);  
	
}