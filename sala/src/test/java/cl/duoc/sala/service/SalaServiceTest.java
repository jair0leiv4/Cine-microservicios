package cl.duoc.sala.service;

import cl.duoc.sala.dto.SalaRequestDTO;
import cl.duoc.sala.dto.SalaResponseDTO;
import cl.duoc.sala.model.ModeloSala;
import cl.duoc.sala.repository.SalaRepository;

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

public class SalaServiceTest {

    // =============================================
    // MOCK: simula el repositorio sin usar MySQL
    // =============================================

    @Mock
    private SalaRepository repository;

    // =============================================
    // SERVICIO A PROBAR con los mocks inyectados
    // =============================================

    @InjectMocks
    private SalaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // =============================================
    // TEST 1 - LISTAR
    // =============================================

    @Test
    @DisplayName("Debe retornar lista de salas")
    void testListar() {

        // GIVEN: repositorio retorna una sala falsa
        ModeloSala sala = new ModeloSala(1L, "Sala A", 100);
        when(repository.findAll()).thenReturn(List.of(sala));

        // WHEN: se invoca listar()
        List<SalaResponseDTO> resultado = service.listar();

        // THEN: la lista no es nula y tiene 1 elemento
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Sala A", resultado.get(0).getNombre());
    }

    // =============================================
    // TEST 2 - BUSCAR POR ID EXISTENTE
    // =============================================

    @Test
    @DisplayName("Debe retornar sala por ID")
    void testBuscarPorId() {

        // GIVEN
        ModeloSala sala = new ModeloSala(1L, "Sala B", 80);
        when(repository.findById(1L)).thenReturn(Optional.of(sala));

        // WHEN
        Optional<SalaResponseDTO> resultado = service.buscarPorId(1L);

        // THEN
        assertTrue(resultado.isPresent());
        assertEquals("Sala B", resultado.get().getNombre());
        assertEquals(80, resultado.get().getCapacidad());
    }

    // =============================================
    // TEST 3 - BUSCAR ID INEXISTENTE
    // =============================================

    @Test
    @DisplayName("Debe retornar Optional vacío si sala no existe")
    void testBuscarPorIdNoExiste() {

        // GIVEN: no existe sala con ID 99
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // WHEN
        Optional<SalaResponseDTO> resultado = service.buscarPorId(99L);

        // THEN
        assertFalse(resultado.isPresent());
    }

    // =============================================
    // TEST 4 - GUARDAR
    // =============================================

    @Test
    @DisplayName("Debe guardar y retornar sala creada")
    void testGuardar() {

        // GIVEN
        SalaRequestDTO dto = new SalaRequestDTO();
        dto.setNombre("Sala VIP");
        dto.setCapacidad(50);

        ModeloSala guardada = new ModeloSala(1L, "Sala VIP", 50);
        when(repository.save(any(ModeloSala.class))).thenReturn(guardada);

        // WHEN
        SalaResponseDTO resultado = service.guardar(dto);

        // THEN
        assertNotNull(resultado);
        assertEquals("Sala VIP", resultado.getNombre());
        assertEquals(50, resultado.getCapacidad());
        verify(repository).save(any(ModeloSala.class));
    }

    // =============================================
    // TEST 5 - ELIMINAR
    // =============================================

    @Test
    @DisplayName("Debe eliminar sala por ID")
    void testEliminar() {

        // GIVEN / WHEN
        service.eliminar(1L);

        // THEN: se verificó que deleteById fue llamado
        verify(repository).deleteById(1L);
    }
}
