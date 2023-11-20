package co.com.ingeneo.api.service.assembler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.com.ingeneo.api.controller.request.ProductoRequest;
import co.com.ingeneo.api.controller.response.ProductoModel;
import co.com.ingeneo.api.repository.domain.Producto;
import co.com.ingeneo.api.service.mapper.ProductoMapper;

/**
 * Prueba Unitarias para evaluar el Assembler de Producto
 * 
 * @author Josue Menendez
 *
 */
public class ProductoAssemblerTest {

	private Producto producto;

	private ProductoAssembler productoAssembler;

	@BeforeEach
	void setUp() throws Exception {
		productoAssembler = new ProductoAssembler(ProductoMapper.INSTANCE);
		producto = new Producto();
		producto.setId(Long.valueOf("5"));
		producto.setDescripcion("Prueba Assembler");
		producto.setNombre("PruebaUnitaria");
	}

	@Test
	@DisplayName("Evaluando el paso de Producto a ProductoModel")
	void testTransformacion() {
		ProductoModel ProductoModel = toModel();
		assertEquals(producto.getId(), ProductoModel.getProductoId());
		assertEquals(producto.getNombre(), ProductoModel.getNombre());
		assertEquals(producto.getDescripcion(), ProductoModel.getDescripcion());
		assertEquals(producto.getPeso(), ProductoModel.getPeso());
	}

	@Test
	@DisplayName("Prueba nulleable cuando el producto es vacio en el assembler")
	void testTransformacionNula() {
		producto = null;
		assertEquals(null, this.toModel());
	}

	@Test
	@DisplayName("Prueba que se obtenga un link de un producto construido")
	void testLinkGenerado() {
		ProductoModel productoModel = toModel();
		assertNotNull(productoModel.getLink("self"));
	}

	@Test
	@DisplayName("Prueba Paso de Request a Entity de Productos")
	void testProductoRequestToProducto() {
		ProductoRequest productoRequest = ProductoRequest.builder().nombre("Prueba 3").descripcion("Descripcion 3")
				.build();
		Producto producto = ProductoMapper.INSTANCE.toProducto(productoRequest);
		assertThat(producto).isNotNull();
		assertThat(producto.getNombre()).isEqualTo("Prueba 3");
		assertThat(producto.getDescripcion()).isEqualTo("Descripcion 3");
		assertThat(producto.getPeso()).isNull();

	}

	private ProductoModel toModel() {
		return productoAssembler.toModel(producto);
	}

}