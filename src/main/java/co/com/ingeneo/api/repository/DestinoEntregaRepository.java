package co.com.ingeneo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.com.ingeneo.api.repository.domain.DestinoEntrega;

public interface DestinoEntregaRepository extends JpaRepository<DestinoEntrega, Long>,JpaSpecificationExecutor<DestinoEntrega> {
   
}