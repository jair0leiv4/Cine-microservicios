package cl.duoc.entrada.service;

import cl.duoc.entrada.dto.EntradaRequestDTO;
import cl.duoc.entrada.dto.EntradaResponseDTO;
import cl.duoc.entrada.model.ModeloEntrada;
import cl.duoc.entrada.repository.EntradaRepository;

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

public class EntradaServiceTest {

    @Mock
    private EntradaRepository repository;

    @InjectMocks
    private EntradaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // =============================================
    // TEST 1 - LISTAR
    // =============================================

    @Test
    @DisplayName("Debe retornar lista de entradas")
    void testListar() {

        // GIVEN
        ModeloEntrada entrada = new ModeloEntrada(1L, "General", 5000, 1L);
        when(repository.findAll()).thenReturn(List.of(entrada));

        // WHEN
        List<EntradaResponseDTO> resultado = service.listar();

        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("General", resultado.get(0).getTipo());
        assertEquals(5000, resultado.get(0).getPrecio());
    }

    // =============================================
    // TEST 2 - BUSCAR POR ID
    // =============================================

    @Test
    @DisplayName("Debe retornar entrada por ID")
    void testBuscarPorId() {

        // GIVEN
        ModeloEntrada entrada = new ModeloEntrada(1L, "VIP", 9000, 2L);
        when(repository.findById(1L)).thenReturn(Optional.of(entrada));

        // WHEN
        EntradaResponseDTO resultado = service.buscarPorId(1L);

        // THEN
        assertNotNull(resultado);
        assertEquals("VIP", resultado.getTipo());
        assertEquals(9000, resultado.getPrecio());
    }

    // =============================================
    // TEST 3 - EXCEPCIÓN POR ID INEXISTENTE
    // =============================================

    @Test
    @DisplayName("Debe lanzar excepción si entrada no existe")
    void testBuscarPorIdNoExiste() {

        // GIVEN
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // WHEN / THEN
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.buscarPorId(99L)
        );
        assertEquals("La entrada no existe", ex.getMessage());
    }

    // =============================================
    // TEST 4 - GUARDAR
    // =============================================

    @Test
    @DisplayName("Debe guardar y retornar entrada creada")
    void testGuardar() {

        // GIVEN
        EntradaRequestDTO dto = new EntradaRequestDTO("Preferencial", 7000, 1L);
        ModeloEntrada guardada = new ModeloEntrada(1L, "Preferencial", 7000, 1L);
        when(repository.save(any(ModeloEntrada.class))).thenReturn(guardada);

        // WHEN
        EntradaResponseDTO resultado = service.guardar(dto);

        // THEN
        assertNotNull(resultado);
        assertEquals("Preferencial", resultado.getTipo());
        verify(repository).save(any(ModeloEntrada.class));
    }

    // =============================================
    // TEST 5 - ELIMINAR CON VALIDACIÓN
    // =============================================

    @Test
    @DisplayName("Debe eliminar entrada existente")
    void testEliminar() {

        // GIVEN
        when(repository.existsById(1L)).thenReturn(true);

        // WHEN
        service.eliminar(1L);

        // THEN
        verify(repository).deleteById(1L);
    }

    // =============================================
    // TEST 6 - ELIMINAR INEXISTENTE LANZA EXCEPCIÓN
    // =============================================

    @Test
    @DisplayName("Debe lanzar excepción al eliminar entrada inexistente")
    void testEliminarNoExiste() {

        // GIVEN
        when(repository.existsById(99L)).thenReturn(false);

        // WHEN / THEN
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.eliminar(99L)
        );
        assertEquals("La entrada no existe", ex.getMessage());
    }
}
