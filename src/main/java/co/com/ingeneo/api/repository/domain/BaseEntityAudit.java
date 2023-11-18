package co.com.ingeneo.api.repository.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntityAudit implements Serializable, HasIdGetter<Long> {

	@Id
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "FECHA_REGISTRO" , nullable = false, updatable = false )
    @CreatedDate
    private LocalDateTime fechaRegistro;
	
	@Getter
	private static final long serialVersionUID = 1675848815055230053L;
	
}