package cl.duoc.pago.service;

import cl.duoc.pago.dto.PagoRequestDTO;
import cl.duoc.pago.dto.PagoResponseDTO;
import cl.duoc.pago.model.ModeloPago;
import cl.duoc.pago.repository.PagoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PagoServiceTest {

    @Mock
    private PagoRepository repository;

    @Mock
    private RestTemplate restTemplate;   // mock para no llamar red real

    @InjectMocks
    private PagoService service;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    // ════════════════════════════════════════════════════
    //  TEST 1 — Listar pagos
    // ════════════════════════════════════════════════════
    @Test
    @DisplayName("Debe retornar lista de pagos")
    void testListar() {
        // GIVEN
        ModeloPago pago = new ModeloPago(1L, "TARJETA", 9000, 1L);
        when(repository.findAll()).thenReturn(List.of(pago));
        // WHEN
        List<PagoResponseDTO> resultado = service.listar();
        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("TARJETA", resultado.get(0).getMetodoPago());
    }

    // ════════════════════════════════════════════════════
    //  TEST 2 — Buscar por ID existente
    // ════════════════════════════════════════════════════
    @Test
    @DisplayName("Debe retornar pago por ID")
    void testBuscarPorId() {
        // GIVEN
        ModeloPago pago = new ModeloPago(2L, "EFECTIVO", 5000, 2L);
        when(repository.findById(2L)).thenReturn(Optional.of(pago));
        // WHEN
        Optional<PagoResponseDTO> resultado = service.buscarPorId(2L);
        // THEN
        assertTrue(resultado.isPresent());
        assertEquals(5000, resultado.get().getMonto());
    }

    // ════════════════════════════════════════════════════
    //  TEST 3 — Validación: método de pago inválido
    // ════════════════════════════════════════════════════
    @Test
    @DisplayName("Debe lanzar excepción si el método de pago es inválido")
    void testMetodoPagoInvalido() {
        // GIVEN
        PagoRequestDTO dto = new PagoRequestDTO();
        dto.setMetodoPago("BITCOIN");
        dto.setMonto(1000);
        dto.setVentaId(1L);
        // WHEN / THEN
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.guardar(dto));
        assertTrue(ex.getMessage().contains("Método de pago inválido"));
    }

    // ════════════════════════════════════════════════════
    //  TEST 4 — Validación: monto negativo
    // ════════════════════════════════════════════════════
    @Test
    @DisplayName("Debe lanzar excepción si el monto es cero o negativo")
    void testMontoInvalido() {
        // GIVEN
        PagoRequestDTO dto = new PagoRequestDTO();
        dto.setMetodoPago("TARJETA");
        dto.setMonto(0);
        dto.setVentaId(1L);
        // WHEN / THEN
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.guardar(dto));
        assertTrue(ex.getMessage().contains("monto"));
    }

    // ════════════════════════════════════════════════════
    //  TEST 5 — Eliminar pago inexistente
    // ════════════════════════════════════════════════════
    @Test
    @DisplayName("Debe lanzar excepción al eliminar pago inexistente")
    void testEliminarNoExiste() {
        // GIVEN
        when(repository.existsById(99L)).thenReturn(false);
        // WHEN / THEN
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.eliminar(99L));
        assertTrue(ex.getMessage().contains("no encontrado"));
    }
}
