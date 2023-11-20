package co.com.ingeneo.api.repository.domain;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "ESTADOS_ORDENES")
public class EstadoOrden extends BaseEntityAudit implements Serializable {

	@Getter
	private static final long serialVersionUID = 1L;

	@Column(name = "CODIGO")
	@Size(max = 10, message = "El campo codigo excede la longitud permitida")
	private String codigo;

	@Column(name = "DESCRIPCION")
	@Size(max = 100, message = "El campo descripcion excede la longitud permitida")
	private String descripcion;
	
	@OneToMany(mappedBy = "estadoOrden", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<Orden> ordenes;

}