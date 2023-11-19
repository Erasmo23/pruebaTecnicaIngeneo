package co.com.ingeneo.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.ingeneo.api.repository.domain.EstadoOrden;
import co.com.ingeneo.api.repository.domain.Orden;

public interface OrdenRepository extends JpaRepository<Orden, Long>,JpaSpecificationExecutor<Orden> {
 
	@Query("SELECT o.estadoOrden FROM Orden o WHERE o=:orden" )
	EstadoOrden findEstadoOrdenByOrdenId(Orden orden);
	
	@Modifying
	@Query("UPDATE Orden x SET x.estadoOrden=:estado where x.id= :ordenId")
	void editEstadoOrden(@Param("estado") EstadoOrden estadoOrden, @Param("ordenId") Long ordenId);
	
	Optional<Orden> findByNumeroGuia(String numeroGuia);
	
}