package co.com.ingeneo.api.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import co.com.ingeneo.api.repository.domain.TipoTransporte;

public interface TipoTransporteRepository extends JpaRepository<TipoTransporte, Long> {

	Integer countById(Long id);

	Slice<TipoTransporte> findByCodigoIgnoreCaseContaining(String q, Pageable page);
}