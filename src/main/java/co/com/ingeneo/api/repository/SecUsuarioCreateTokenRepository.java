package co.com.ingeneo.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.ingeneo.api.repository.domain.SecUsuarioCreateToken;

public interface SecUsuarioCreateTokenRepository extends JpaRepository<SecUsuarioCreateToken, Long> {
 
	Optional<SecUsuarioCreateToken> findByToken(String token);
	
}