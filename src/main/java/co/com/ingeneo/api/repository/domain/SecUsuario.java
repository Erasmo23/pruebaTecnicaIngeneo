package co.com.ingeneo.api.repository.domain;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
@Table(name = "SEC_USUARIOS")
public class SecUsuario extends BaseEntityAudit implements Serializable {

	@Getter
	private static final long serialVersionUID = 1L;

	@Column(name = "NOMBRES")
	private String nombres;

	@Column(name = "APELLIDOS")
	private String apellidos;

	@Column(name = "CORREO", unique = true)
	private String correo;

	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "ACTIVO")
	private Boolean activo;
	
	@Transient
	private transient String tokenTemp;

	@OneToMany(mappedBy = "secUsuario", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private Set<SecUsuarioCreateToken> secUsuarioCreateTokens;

	@OneToMany(mappedBy = "secUsuario", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private Set<SecUsuarioRol> secUsuarioRoles;
	
	
	public String nombreCompleto() {
		if (this.apellidos != null) {
			return this.nombres.concat(" " ).concat(this.apellidos);
		}
		return this.nombres;
	}

}