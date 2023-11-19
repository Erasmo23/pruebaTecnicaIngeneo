package co.com.ingeneo.api.controller.response;

import java.math.BigDecimal;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
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
public class ProductoModel extends RepresentationModel<ProductoModel> {

	private Integer productoId;
	
	private String nombre; 
	
	private String descripcion; 

    private BigDecimal peso; 
	
}