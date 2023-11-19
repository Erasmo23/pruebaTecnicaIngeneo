package co.com.ingeneo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.ingeneo.api.repository.domain.Pais;

public interface PaisRepository extends JpaRepository<Pais, Long> {
	
	Integer countById(Long id);
   
}