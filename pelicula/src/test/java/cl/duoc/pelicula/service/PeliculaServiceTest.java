package cl.duoc.pelicula.service;

import cl.duoc.pelicula.dto.PeliculaRequestDTO;
import cl.duoc.pelicula.dto.PeliculaResponseDTO;
import cl.duoc.pelicula.model.ModeloPelicula;
import cl.duoc.pelicula.repository.PeliculaRepository;

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

public class PeliculaServiceTest {

    @Mock private PeliculaRepository repository;
    @Mock private RestTemplate restTemplate;  // mock para no llamar red real
    @InjectMocks private PeliculaService service;

    @BeforeEach void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    @DisplayName("Debe retornar lista de películas")
    void testListar() {
        // GIVEN
        ModeloPelicula p = new ModeloPelicula(1L, "Avengers", "Acción", 120, 1L);
        when(repository.findAll()).thenReturn(List.of(p));
        // WHEN
        List<PeliculaResponseDTO> resultado = service.listar();
        // THEN
        assertEquals(1, resultado.size());
        assertEquals("Avengers", resultado.get(0).getTitulo());
    }

    @Test
    @DisplayName("Debe retornar Optional vacío si película no existe")
    void testBuscarNoExiste() {
        // GIVEN
        when(repository.findById(99L)).thenReturn(Optional.empty());
        // WHEN
        Optional<PeliculaResponseDTO> resultado = service.buscarPorId(99L);
        // THEN
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Debe lanzar excepción si la duración es cero o negativa")
    void testDuracionInvalida() {
        // GIVEN
        PeliculaRequestDTO dto = new PeliculaRequestDTO();
        dto.setTitulo("Test");
        dto.setDuracion(0);
        dto.setCategoriaId(1L);
        // WHEN / THEN
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.guardar(dto));
        assertTrue(ex.getMessage().contains("duración"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si la categoría no existe (mock devuelve error)")
    void testCategoriaInexistente() {
        // GIVEN: RestTemplate lanza excepción simulando servicio caído
        PeliculaRequestDTO dto = new PeliculaRequestDTO();
        dto.setTitulo("Test");
        dto.setDuracion(90);
        dto.setCategoriaId(99L);
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenThrow(new RuntimeException("connection refused"));
        // WHEN / THEN
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.guardar(dto));
        assertTrue(ex.getMessage().contains("categoría"));
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar película inexistente")
    void testEliminarNoExiste() {
        // GIVEN
        when(repository.existsById(99L)).thenReturn(false);
        // WHEN / THEN
        assertThrows(RuntimeException.class, () -> service.eliminar(99L));
    }
}
