package co.com.ingeneo.api.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.ingeneo.api.config.CommonConfigurationUtils;
import co.com.ingeneo.api.config.security.service.JWTService;
import co.com.ingeneo.api.controller.request.ProductoRequest;
import co.com.ingeneo.api.controller.response.ProductoModel;
import co.com.ingeneo.api.repository.domain.Producto;
import co.com.ingeneo.api.service.ProductoService;
import co.com.ingeneo.api.service.assembler.ProductoAssembler;
import co.com.ingeneo.api.service.mapper.ProductoMapper;

@WebMvcTest(ProductoController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductoControllerTest extends AbstractTestController {

	@MockBean
	private ProductoService productoService;

	@MockBean
	private JWTService jwtService;

	private ProductoAssembler productoAssembler;

	private Producto producto;

	@BeforeEach
	public void initialize() {

		productoAssembler = new ProductoAssembler(ProductoMapper.INSTANCE);

		producto = new Producto();
		producto.setId(123L);
		producto.setNombre(CommonConfigurationUtils.generarStringAlfanumericoAleatorio(20));
		producto.setDescripcion(CommonConfigurationUtils.generarStringAlfanumericoAleatorio(35));
		producto.setFechaRegistro(LocalDateTime.now());
	}

	@Test
	void testGetOne() throws Exception {

		when(productoService.getProductoById(anyLong())).thenReturn(productoAssembler.toModel(producto));

        getMockMvc().perform(get("/api/v1/productos/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.descripcion").exists())
                .andExpect(jsonPath("$.productoId").value("123"));

        verify(productoService).getProductoById(anyLong());
	}

	@Test
	void testSaveEntity() throws JsonProcessingException, Exception {

		final ProductoRequest productoRequest = ProductoRequest.builder().nombre("PruebaUnitaria").descripcion("JUnit")
				.peso(BigDecimal.ONE).build();
		final ProductoModel productoModel = ProductoModel.builder().productoId(12L).nombre("pruebaUnitaria").build();

		when(productoService.save(any(ProductoRequest.class))).thenReturn(productoModel);

		getMockMvc()
				.perform(post("/api/v1/productos").contentType(MediaType.APPLICATION_JSON)
						.content(getObjectMapper().writeValueAsString(productoRequest)))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
				.andExpect(jsonPath("$.productoId", is(12))).andExpect(jsonPath("$.nombre", is("pruebaUnitaria")))
				.andExpect(jsonPath("$.peso").isEmpty());
		verify(productoService, times(1)).save(any(ProductoRequest.class));

	}

	@Test
	void testDelete() throws JsonProcessingException, Exception {

		doNothing().when(productoService).deleteById(anyLong());

		getMockMvc().perform(delete("/api/v1/productos/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
		verify(productoService, times(1)).deleteById(anyLong());

	}

}