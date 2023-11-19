package co.com.ingeneo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.ingeneo.api.repository.domain.EstadoOrden;

public interface EstadoOrdenRepository extends JpaRepository<EstadoOrden, Long> {

	EstadoOrden findByCodigo(String codigo);
   
}