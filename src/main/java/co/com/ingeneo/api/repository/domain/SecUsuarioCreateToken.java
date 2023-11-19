package co.com.ingeneo.api.repository.domain;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "SEC_USUARIO_CREATE_TOKEN")
public class SecUsuarioCreateToken extends BaseEntityAudit implements Serializable {
   
	@Getter
    private static final long serialVersionUID = 1L;
	
	public SecUsuarioCreateToken(SecUsuario secUsuario) {
		this.secUsuario = secUsuario;
		this.token = UUID.randomUUID().toString();
	}

    @Column(name = "TOKEN" )
    @NotBlank(message = "No puede estar vacio el campo token")
    @Size(max = 255, message = "El campo token excede la longitud permitida")
    private String token; 

    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private SecUsuario secUsuario; 

}