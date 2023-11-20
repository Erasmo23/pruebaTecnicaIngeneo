package co.com.ingeneo.api.repository.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "ORDENES")
public class Orden extends BaseEntityAudit implements Serializable {
	
    @Getter
    private static final long serialVersionUID = 1L;

    @Column(name = "CANTIDAD_PRODUCTO" )
    private Integer cantidadProducto; 

    @Column(name = "FECHA_ENTREGA" )
    private LocalDate fechaEntrega; 

    @Column(name = "NUMERO_GUIA" )
    private String numeroGuia; 

    @Column(name = "PRECIO_ENVIO_ORIGINAL" , scale = 2 )
    private BigDecimal precioEnvioOriginal; 

    @Column(name = "PRECIO_ENVIO_DESCUENTO" , scale = 2 )
    private BigDecimal precioEnvioDescuento; 

    @Column(name = "IDENTIFICACION_TRANSPORTE" )
    private String identificacionTransporte; 

    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @JoinColumn(name = "ID_DESTINO_ENTREGA", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private DestinoEntrega destinoEntrega; 

    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Producto producto; 

    @JoinColumn(name = "ID_ESTADO_ORDEN", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private EstadoOrden estadoOrden;

}