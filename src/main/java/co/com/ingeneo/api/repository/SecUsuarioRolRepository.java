package co.com.ingeneo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.ingeneo.api.repository.domain.SecRol;
import co.com.ingeneo.api.repository.domain.SecUsuarioRol;

public interface SecUsuarioRolRepository extends JpaRepository<SecUsuarioRol, Long> {
	
	@Query("SELECT x FROM SecRol x WHERE x.codigo=:codigo")
	SecRol findSecRolByCodigo(@Param("codigo") String  codigo);
 
}