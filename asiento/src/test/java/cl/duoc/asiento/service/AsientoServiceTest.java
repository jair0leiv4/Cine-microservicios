package cl.duoc.asiento.service;

import cl.duoc.asiento.dto.AsientoRequestDTO;
import cl.duoc.asiento.dto.AsientoResponseDTO;
import cl.duoc.asiento.model.ModeloAsiento;
import cl.duoc.asiento.repository.AsientoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AsientoServiceTest {

    @Mock private AsientoRepository repository;
    @Mock private RestTemplate restTemplate;
    @InjectMocks private AsientoService service;

    @BeforeEach void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    @DisplayName("Debe retornar lista de asientos")
    void testListar() {
        // GIVEN
        ModeloAsiento a = new ModeloAsiento(1L, "A1", 1L);
        when(repository.findAll()).thenReturn(List.of(a));
        // WHEN
        List<AsientoResponseDTO> resultado = service.listar();
        // THEN
        assertEquals(1, resultado.size());
        assertEquals("A1", resultado.get(0).getNumero());
    }

    @Test
    @DisplayName("Debe lanzar excepción si asiento no existe")
    void testBuscarNoExiste() {
        // GIVEN
        when(repository.findById(99L)).thenReturn(java.util.Optional.empty());
        // WHEN / THEN
        assertThrows(RuntimeException.class, () -> service.buscarPorId(99L));
    }

    @Test
    @DisplayName("Debe lanzar excepción si la sala referenciada no existe")
    void testSalaInexistente() {
        // GIVEN: RestTemplate simula que la sala no existe
        AsientoRequestDTO dto = new AsientoRequestDTO();
        dto.setNumero("B2");
        dto.setSalaId(99L);
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenThrow(new RuntimeException("404"));
        // WHEN / THEN
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.guardar(dto));
        assertTrue(ex.getMessage().contains("no existe"));
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar asiento inexistente")
    void testEliminarNoExiste() {
        // GIVEN
        when(repository.existsById(99L)).thenReturn(false);
        // WHEN / THEN
        assertThrows(RuntimeException.class, () -> service.eliminar(99L));
    }
}
