package cl.duoc.producto.service;

import cl.duoc.producto.dto.ProductoRequestDTO;
import cl.duoc.producto.dto.ProductoResponseDTO;
import cl.duoc.producto.model.ModeloProducto;
import cl.duoc.producto.repository.ProductoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    @Mock private ProductoRepository repository;
    @InjectMocks private ProductoService service;

    @BeforeEach void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    @DisplayName("Debe retornar lista de productos")
    void testListar() {
        // GIVEN
        ModeloProducto p = new ModeloProducto(1L, "Popcorn", 50, 3500);
        when(repository.findAll()).thenReturn(List.of(p));
        // WHEN
        List<ProductoResponseDTO> resultado = service.listar();
        // THEN
        assertEquals(1, resultado.size());
        assertEquals("Popcorn", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Debe lanzar excepción si precio es cero o negativo")
    void testPrecioInvalido() {
        // GIVEN
        ProductoRequestDTO dto = new ProductoRequestDTO("Nachos", 10, 0);
        // WHEN / THEN
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.guardar(dto));
        assertTrue(ex.getMessage().contains("precio"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si stock es negativo")
    void testStockNegativo() {
        // GIVEN
        ProductoRequestDTO dto = new ProductoRequestDTO("Bebida", -1, 2000);
        // WHEN / THEN
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.guardar(dto));
        assertTrue(ex.getMessage().contains("stock"));
    }

    @Test
    @DisplayName("Debe guardar producto válido")
    void testGuardar() {
        // GIVEN
        ProductoRequestDTO dto = new ProductoRequestDTO("Nachos", 30, 4000);
        ModeloProducto guardado = new ModeloProducto(1L, "Nachos", 30, 4000);
        when(repository.save(any())).thenReturn(guardado);
        // WHEN
        ProductoResponseDTO resultado = service.guardar(dto);
        // THEN
        assertEquals("Nachos", resultado.getNombre());
        verify(repository).save(any());
    }

    @Test
    @DisplayName("Debe retornar Optional vacío si producto no existe")
    void testBuscarNoExiste() {
        // GIVEN
        when(repository.findById(99L)).thenReturn(Optional.empty());
        // WHEN
        Optional<ProductoResponseDTO> resultado = service.buscarPorId(99L);
        // THEN
        assertFalse(resultado.isPresent());
    }
}
