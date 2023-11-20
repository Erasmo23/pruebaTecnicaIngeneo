package co.com.ingeneo.api.repository.domain;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "CLIENTES")
public class Cliente extends BaseEntityAudit implements Serializable {

	@Column(name = "NOMBRES", nullable = false )
    private String nombres;

    @Column(name = "APELLIDOS" )
    private String apellidos;

    @Column(name = "CORREO" ,nullable = false, unique = true)
    private String correo;

    @Column(name = "TELEFONO" )
    private String telefono;

    @Column(name = "DIRECCION", nullable = false )
    private String direccion;

    @Column(name = "DIRECCION_FACTURACION" )
    private String direccionFacturacion;
    
    public String nombreCompleto() {
    	if (this.apellidos!=null) {
    		return this.nombres.concat(" ").concat(this.apellidos);
    	}
    	return this.nombres;
    }
    
    @Getter
	private static final long serialVersionUID = 834977885361469619L;
	
}