package co.com.ingeneo.api.controller.response;

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
public class ClienteModel extends RepresentationModel<ClienteModel> {

	private Long clienteId;

    private String nombres;

    private String apellidos;

    private String correo;

    private String telefono;

    private String direccion;

    private String direccionFacturacion;
	
}