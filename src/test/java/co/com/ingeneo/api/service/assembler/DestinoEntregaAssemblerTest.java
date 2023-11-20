package co.com.ingeneo.api.service.assembler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.com.ingeneo.api.controller.request.DestinoEntregaRequest;
import co.com.ingeneo.api.controller.response.DestinoEntregaModel;
import co.com.ingeneo.api.repository.domain.DestinoEntrega;
import co.com.ingeneo.api.repository.domain.Pais;
import co.com.ingeneo.api.repository.domain.TipoTransporte;
import co.com.ingeneo.api.service.mapper.DestinoEntregaMapper;

/**
 * Prueba Unitarias para evaluar el Assembler de DestinoEntrega
 * 
 * @author Josue Menendez
 *
 */
class DestinoEntregaAssemblerTest {
	
	private DestinoEntregaAssembler destinoEntregaAssembler;

	private DestinoEntrega destinoEntrega;

	@BeforeEach
	void setUp() throws Exception {
		
		final Long longTest = 5L;
		final String stringTest = "testing";
		
		this.destinoEntregaAssembler = new DestinoEntregaAssembler(DestinoEntregaMapper.INSTANCE);
		
		TipoTransporte tipoTransporte = new TipoTransporte();
		tipoTransporte.setId(longTest);
		tipoTransporte.setCodigo(stringTest);
		tipoTransporte.setDescripcion(stringTest);
		
		Pais pais = new Pais();
		pais.setId(longTest);
		pais.setCodigoIso("SV");
		pais.setNombre(stringTest);
		
		this.destinoEntrega = new DestinoEntrega();
		this.destinoEntrega.setId(longTest);
		this.destinoEntrega.setContacto(stringTest);
		this.destinoEntrega.setPais(pais);
		this.destinoEntrega.setTipoTransporte(tipoTransporte);
		
	}

	@Test
	void testTransformacionEntityToModelDestinoEntrega() {
		DestinoEntregaModel model = toModel();
		assertEquals(destinoEntrega.getId(), model.getDestinoEntregaId());
		assertEquals(destinoEntrega.getContacto(), model.getContacto());
		assertNotNull(model.getPais());
		assertNotNull(model.getTipoTransporte());
		assertNull(model.getTelefono());
	}
	
	@Test
	void testTransformacionEntityToModelDestinoEntregaNula() {
		destinoEntrega= null;
		assertNull(this.toModel());
	}
	
	@Test
	void testLinkGeneradoEnTransformacionDestinoEntregaModel() {
		DestinoEntregaModel productoModel = toModel();
		assertNotNull(productoModel.getLink("self"));
	}
	
	@Test
	@DisplayName("Prueba Paso de Request a Entity de Productos")
	void testTransformacionRequestoToEntity() {
		
		DestinoEntregaRequest destinoEntregaRequest = DestinoEntregaRequest.builder().nombre("Prueba 3")
				.direccion("JUnit").paisId(2L).tipoTransporteId(1L).build();
		
		DestinoEntrega destinoEntrega = DestinoEntregaMapper.INSTANCE.toDestinoEntrega(destinoEntregaRequest);
		assertThat(destinoEntrega).isNotNull();
		assertThat(destinoEntrega.getTipoTransporte()).isNotNull();
		assertThat(destinoEntrega.getPais().getId()).isNotNull();
		assertThat(destinoEntrega.getNombre()).isEqualTo("Prueba 3");
		assertThat(destinoEntrega.getDireccion()).isEqualTo("JUnit");
		assertThat(destinoEntrega.getTelefono()).isNull();

	}
	
	private DestinoEntregaModel toModel() {
		return destinoEntregaAssembler.toModel(destinoEntrega);
	}

}