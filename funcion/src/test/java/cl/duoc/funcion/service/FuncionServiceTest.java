package cl.duoc.funcion.service;

import cl.duoc.funcion.dto.FuncionRequestDTO;
import cl.duoc.funcion.dto.FuncionResponseDTO;
import cl.duoc.funcion.model.ModeloFuncion;
import cl.duoc.funcion.repository.FuncionRepository;

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

public class FuncionServiceTest {

    @Mock private FuncionRepository repository;
    @InjectMocks private FuncionService service;

    @BeforeEach void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    @DisplayName("Debe retornar lista de funciones")
    void testListar() {
        // GIVEN
        ModeloFuncion f = new ModeloFuncion(1L, "18:00", 1L, 2L);
        when(repository.findAll()).thenReturn(List.of(f));
        // WHEN
        List<FuncionResponseDTO> resultado = service.listar();
        // THEN
        assertEquals(1, resultado.size());
        assertEquals("18:00", resultado.get(0).getHora());
    }

    @Test
    @DisplayName("Debe retornar función por ID")
    void testBuscarPorId() {
        // GIVEN
        ModeloFuncion f = new ModeloFuncion(1L, "20:30", 2L, 3L);
        when(repository.findById(1L)).thenReturn(Optional.of(f));
        // WHEN
        Optional<FuncionResponseDTO> resultado = service.buscarPorId(1L);
        // THEN
        assertTrue(resultado.isPresent());
        assertEquals("20:30", resultado.get().getHora());
    }

    @Test
    @DisplayName("Debe lanzar excepción si hora tiene formato inválido")
    void testHoraInvalida() {
        // GIVEN
        FuncionRequestDTO dto = new FuncionRequestDTO("25:99", 1L, 1L);
        // WHEN / THEN
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.guardar(dto));
        assertTrue(ex.getMessage().contains("Hora inválida"));
    }

    @Test
    @DisplayName("Debe guardar función con hora válida")
    void testGuardar() {
        // GIVEN
        FuncionRequestDTO dto = new FuncionRequestDTO("22:00", 1L, 1L);
        ModeloFuncion guardada = new ModeloFuncion(1L, "22:00", 1L, 1L);
        when(repository.save(any())).thenReturn(guardada);
        // WHEN
        FuncionResponseDTO resultado = service.guardar(dto);
        // THEN
        assertNotNull(resultado);
        assertEquals("22:00", resultado.getHora());
        verify(repository).save(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar función inexistente")
    void testActualizarNoExiste() {
        // GIVEN
        when(repository.findById(99L)).thenReturn(Optional.empty());
        FuncionRequestDTO dto = new FuncionRequestDTO("10:00", 1L, 1L);
        // WHEN / THEN
        assertThrows(RuntimeException.class, () -> service.actualizar(99L, dto));
    }

    @Test
    @DisplayName("Debe eliminar función existente")
    void testEliminar() {
        // GIVEN
        when(repository.existsById(1L)).thenReturn(true);
        // WHEN
        service.eliminar(1L);
        // THEN
        verify(repository).deleteById(1L);
    }
}
