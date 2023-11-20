package co.com.ingeneo.api.repository.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "PRODUCTOS")
public class Producto extends BaseEntityAudit implements Serializable {

	@Getter
	private static final long serialVersionUID = 1L;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "PESO", scale = 2)
	private BigDecimal peso;

	@OneToMany(mappedBy = "producto", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private Set<Orden> ordenes;

}