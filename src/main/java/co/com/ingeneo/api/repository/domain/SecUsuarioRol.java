package co.com.ingeneo.api.repository.domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "SEC_USUARIO_ROL")
public class SecUsuarioRol extends BaseEntityAudit implements Serializable {
    @Getter
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private SecUsuario secUsuario; 

    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private SecRol secRol; 

}