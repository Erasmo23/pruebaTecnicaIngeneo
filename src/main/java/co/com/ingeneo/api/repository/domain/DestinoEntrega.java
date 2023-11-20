package co.com.ingeneo.api.repository.domain;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "DESTINOS_ENTREGA")
public class DestinoEntrega extends BaseEntityAudit implements Serializable {

	@Getter
	private static final long serialVersionUID = 1L;
	
	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "DIRECCION")
	private String direccion;

	@Column(name = "CONTACTO")
	private String contacto;

	@Column(name = "TELEFONO")
	private String telefono;

	@OneToMany(mappedBy = "destinoEntrega", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private Set<Orden> ordenes;

	@JoinColumn(name = "ID_PAIS", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Pais pais;

	@JoinColumn(name = "ID_TIPO_TRANSPORTE", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private TipoTransporte tipoTransporte;

}