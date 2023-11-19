package co.com.ingeneo.api.controller.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonFormat;

import co.com.ingeneo.api.config.CommonConfigurationUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@Builder(toBuilder = true)
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "data")
public class OrdenModel extends RepresentationModel<OrdenModel> {
	
	private Integer ordenId;

	private ClienteModel cliente;
	
	private ProductoModel producto;
	
	private Integer cantidadProducto;
	
	private DestinoEntregaModel destinoEntrega;
	
	@JsonFormat(pattern = CommonConfigurationUtils.DATE_FORMAT)
	private LocalDate fechaEntrega;
	
	private String numeroGuia;
	
	private BigDecimal precioEnvioOriginal;
	
	private BigDecimal precioEnvioDescuento;
	
	private String identificacionTransporte;
	
}