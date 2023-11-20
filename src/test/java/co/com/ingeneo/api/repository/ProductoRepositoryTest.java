package co.com.ingeneo.api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import co.com.ingeneo.api.config.CommonConfigurationUtils;
import co.com.ingeneo.api.repository.domain.Producto;

@DataJpaTest
@ActiveProfiles("test")
class ProductoRepositoryTest {

	@Autowired
	ProductoRepository productoRepository;

	@BeforeEach
	void init() {
		Producto producto = new Producto();
		producto.setId(1L);
		producto.setNombre(CommonConfigurationUtils.generarStringAlfanumericoAleatorio(10));
		producto.setDescripcion(CommonConfigurationUtils.generarStringAlfanumericoAleatorio(10));
		producto.setPeso(BigDecimal.ZERO);
		producto.setFechaRegistro(LocalDateTime.now());
		productoRepository.save(producto);
	}
	
	@AfterEach
    void clean() {
		productoRepository.deleteAll();
    }

	@Order(1)
	@Test
	void testFindProducto() {
		Producto producto = productoRepository.findAll().stream().findFirst().get();
		assertNotNull(producto);
		assertNotNull(producto.getDescripcion());
		assertEquals("0", producto.getPeso().toPlainString());
	}
	
	@Test
	void testFindProductoException() {
		Optional<Producto> producto = productoRepository.findById(99L);
        assertThrows(NoSuchElementException.class, producto::orElseThrow);
        assertFalse(producto.isPresent());
	}
	
	@Order(3)
	@Test
	void testAll() {
		List<Producto> list = productoRepository.findAll();
		assertNotNull(list);
		assertEquals(1, list.size());
	}
	
	@Order(4)
	@Test
	void update() {
		
		Producto producto = productoRepository.findAll().stream().findFirst().get();
		assertNotNull(producto);
		assertEquals("0", producto.getPeso().toPlainString());
		
		producto.setPeso(new BigDecimal("152.25"));
		productoRepository.save(producto);
		
		this.productoRepository.flush();
	    
	    Producto productoFetched = this.productoRepository.findById(producto.getId()).orElseThrow();
	    
	    assertEquals("152.25", productoFetched.getPeso().toPlainString());
		
	}

}