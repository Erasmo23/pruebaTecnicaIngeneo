package co.com.ingeneo.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.util.UriComponentsBuilder;

import co.com.ingeneo.api.config.exception.RecursoNoEncontrado;
import co.com.ingeneo.api.controller.request.ProductoRequest;
import co.com.ingeneo.api.controller.response.ProductoModel;
import co.com.ingeneo.api.repository.ProductoRepository;
import co.com.ingeneo.api.repository.domain.Producto;
import co.com.ingeneo.api.service.assembler.ProductoAssembler;
import co.com.ingeneo.api.service.implementation.ProductoServiceImpl;
import co.com.ingeneo.api.service.mapper.ProductoMapper;

class ProductoServiceTest {

	private ProductoService productoService;

	private ProductoRepository productoRepository;

	private ProductoAssembler productoAssembler;

	private PagedResourcesAssembler<Producto> pagedResourcesAssembler;

	@BeforeEach
	void initialize() {
		productoAssembler = new ProductoAssembler(ProductoMapper.INSTANCE);
		pagedResourcesAssembler = new PagedResourcesAssembler<Producto>(null,
				UriComponentsBuilder.fromUriString("http://localhost").build());

		productoRepository = Mockito.mock(ProductoRepository.class);
		productoService = new ProductoServiceImpl(productoRepository, productoAssembler, pagedResourcesAssembler);
	}

	@Nested
	@DisplayName("GetProductoModel")
	class GetProductoModel {

		private Optional<Producto> optionalproducto;
		private ProductoModel productoModel;

		@BeforeEach
		void initialize() {
			Producto producto = new Producto();
			producto.setId(1L);
			producto.setNombre("Prueba Unitaria");
			producto.setDescripcion("Prueba Unitaria Descripcion");
			producto.setPeso(new BigDecimal("15.25"));
			optionalproducto = Optional.of(producto);
			productoModel = productoAssembler.toModel(producto);
		}

		@Test
		@DisplayName("Devuelve el ProductoModel del servicio")
		void getProductoModelSuccess() {
			assertEquals(productoModel, getRegistroProductoModel());
		}

		@Test
        @DisplayName("Devuelve Excepcion al no encontrar ID")
        void getExceptionRegistroNoEncontrado() {
            
        	when(productoRepository.findById(anyLong())).thenReturn(Optional.empty());
        	
        	Exception exception =  assertThrows(RecursoNoEncontrado.class, () -> {
            	productoService.getProductoById(4L);
        	});
        	
        	assertNotNull(exception);
            assertTrue(exception instanceof RecursoNoEncontrado);
            assertEquals("Producto con identificador 4 no encontrado", exception.getLocalizedMessage());
        	
        }

		private ProductoModel getRegistroProductoModel() {
			doReturn(optionalproducto).when(productoRepository).findById(anyLong());

			return productoService.getProductoById(1L);
		}

	}
	
	@Nested
    @DisplayName("Crear Producto")
    class CreateProducto {

        private ProductoRequest productoRequest;

        @BeforeEach
        void initialize() {
        	productoRequest = ProductoRequest.builder().nombre("JUnit").descripcion("Descripcion").peso(new BigDecimal("12.2")).build();
        }

        @Test
        @DisplayName("Debe crear un registro cuando se proporcione un Producto")
        void createProducto() {
            this.createProductoService();
            verify(productoRepository, times(1)).save(any(Producto.class));
        }

        @Test
        @DisplayName("Debe de lanzar excepcion porque se envia null")
        void createProductoExceptionIsNull() {
        	productoRequest = null;
        	when(productoRepository.save(null)).thenThrow(IllegalArgumentException.class);
            assertThrows(IllegalArgumentException.class, this::createProductoService);
        }

        private void createProductoService() {
        	productoService.save(productoRequest);
        }
    }
	
	@Nested
    @DisplayName("GetListaProductos")
    class GetPaginado {

        @Test
        @DisplayName("Should return a page when repository is paged")
        void shouldReturnAPageWhenRepositoryIsPaged() {
            assertNotNull(getAllProductos());
        }

        @SuppressWarnings("unchecked")
		private PagedModel<ProductoModel> getAllProductos() {
            doReturn(new PageImpl<Producto>(Collections.emptyList()))
                .when(productoRepository).findAll(any(Specification.class),any(Pageable.class));

            return productoService.getAllProductoFiltered(0, 10,null, null);
        }
    }

}