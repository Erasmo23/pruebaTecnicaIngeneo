package co.com.ingeneo.api.repository.domain;

import java.io.Serializable;
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
@Table(name = "TIPO_TRANSPORTE")
public class TipoTransporte extends BaseEntityAudit implements Serializable {
	
    @Getter
    private static final long serialVersionUID = 1L;

    @Column(name = "CODIGO" )
    private String codigo; 

    @Column(name = "DESCRIPCION" )
    private String descripcion; 

    @Column(name = "LABEL_APP" )
    private String labelApp; 

    @OneToMany(mappedBy = "tipoTransporte", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<DestinoEntrega> destinosEntregas;

}