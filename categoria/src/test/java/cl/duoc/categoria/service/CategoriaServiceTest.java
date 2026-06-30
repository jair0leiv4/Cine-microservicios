package cl.duoc.categoria.service;

import cl.duoc.categoria.DTO.CategoriaRequestDTO;
import cl.duoc.categoria.DTO.CategoriaResponseDTO;
import cl.duoc.categoria.model.ModeloCategoria;
import cl.duoc.categoria.repository.CategoriaRepository;

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

public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repository;

    @InjectMocks
    private CategoriaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // =============================================
    // TEST 1 - LISTAR
    // =============================================

    @Test
    @DisplayName("Debe retornar todas las categorias")
    void testObtenerTodas() {

        // GIVEN
        ModeloCategoria cat = new ModeloCategoria(1L, "Acción", "Películas de acción");
        when(repository.findAll()).thenReturn(List.of(cat));

        // WHEN
        List<CategoriaResponseDTO> resultado = service.obtenerTodas();

        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Acción", resultado.get(0).getNombre());
    }

    // =============================================
    // TEST 2 - BUSCAR POR ID
    // =============================================

    @Test
    @DisplayName("Debe retornar categoria por ID")
    void testObtenerPorId() {

        // GIVEN
        ModeloCategoria cat = new ModeloCategoria(1L, "Drama", "Películas de drama");
        when(repository.findById(1L)).thenReturn(Optional.of(cat));

        // WHEN
        Optional<CategoriaResponseDTO> resultado = service.obtenerPorId(1L);

        // THEN
        assertTrue(resultado.isPresent());
        assertEquals("Drama", resultado.get().getNombre());
    }

    // =============================================
    // TEST 3 - ID INEXISTENTE
    // =============================================

    @Test
    @DisplayName("Debe retornar Optional vacío si categoria no existe")
    void testObtenerPorIdNoExiste() {

        // GIVEN
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // WHEN
        Optional<CategoriaResponseDTO> resultado = service.obtenerPorId(99L);

        // THEN
        assertFalse(resultado.isPresent());
    }

    // =============================================
    // TEST 4 - GUARDAR
    // =============================================

    @Test
    @DisplayName("Debe guardar y retornar categoria creada")
    void testGuardar() {

        // GIVEN
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Terror", "Películas de terror");
        ModeloCategoria guardada = new ModeloCategoria(1L, "Terror", "Películas de terror");
        when(repository.save(any(ModeloCategoria.class))).thenReturn(guardada);

        // WHEN
        CategoriaResponseDTO resultado = service.guardar(dto);

        // THEN
        assertNotNull(resultado);
        assertEquals("Terror", resultado.getNombre());
        verify(repository).save(any(ModeloCategoria.class));
    }

    // =============================================
    // TEST 5 - ELIMINAR
    // =============================================

    @Test
    @DisplayName("Debe eliminar categoria por ID")
    void testEliminar() {

        // GIVEN / WHEN
        service.eliminar(1L);

        // THEN
        verify(repository).deleteById(1L);
    }
}
