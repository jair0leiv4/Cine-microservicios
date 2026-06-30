package cl.duoc.pelicula.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.duoc.pelicula.dto.PeliculaRequestDTO;
import cl.duoc.pelicula.dto.PeliculaResponseDTO;
import cl.duoc.pelicula.model.ModeloPelicula;
import cl.duoc.pelicula.repository.PeliculaRepository;
import lombok.RequiredArgsConstructor;

/**
 * Capa de negocio del microservicio Pelicula.
 *
 * Responsabilidades:
 *  - CRUD completo de películas.
 *  - Validar que la categoría exista antes de
 *    crear o actualizar (REST → microservicio Categoria).
 *  - Reglas: duración mínima 1 minuto, título no vacío.
 */
@Service
@RequiredArgsConstructor
public class PeliculaService {

    private final PeliculaRepository repository;
    private final RestTemplate restTemplate;

    // ─── URL base del microservicio Categoria ────────────
    private static final String CATEGORIA_URL =
            "http://localhost:8084/api/categorias/";

    // ════════════════════════════════════════════════════
    //  Conversión Entity → DTO de respuesta
    // ════════════════════════════════════════════════════
    private PeliculaResponseDTO mapToDTO(ModeloPelicula p) {
        return new PeliculaResponseDTO(
                p.getId(), p.getTitulo(),
                p.getGenero(), p.getDuracion(), p.getCategoriaId());
    }

    // ════════════════════════════════════════════════════
    //  Regla de negocio: la categoría debe existir
    // ════════════════════════════════════════════════════
    private void validarCategoria(Long categoriaId) {
        try {
            ResponseEntity<String> res =
                    restTemplate.getForEntity(CATEGORIA_URL + categoriaId, String.class);
            if (!res.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("La categoría con ID " + categoriaId + " no existe");
            }
        } catch (Exception e) {
            throw new RuntimeException("La categoría con ID " + categoriaId + " no existe");
        }
    }

    // ─── Regla de negocio: duración mínima ───────────────
    private void validarDuracion(Integer duracion) {
        if (duracion == null || duracion < 1) {
            throw new RuntimeException("La duración debe ser al menos 1 minuto");
        }
    }

    // ════════════════════════════════════════════════════
    //  LISTAR — GET /api/peliculas
    // ════════════════════════════════════════════════════
    public List<PeliculaResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ════════════════════════════════════════════════════
    //  BUSCAR POR ID — GET /api/peliculas/{id}
    // ════════════════════════════════════════════════════
    public Optional<PeliculaResponseDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    // ════════════════════════════════════════════════════
    //  CREAR — POST /api/peliculas
    // ════════════════════════════════════════════════════
    public PeliculaResponseDTO guardar(PeliculaRequestDTO dto) {
        validarDuracion(dto.getDuracion());
        validarCategoria(dto.getCategoriaId());

        ModeloPelicula pelicula = new ModeloPelicula(
                null, dto.getTitulo(), dto.getGenero(),
                dto.getDuracion(), dto.getCategoriaId());

        return mapToDTO(repository.save(pelicula));
    }

    // ════════════════════════════════════════════════════
    //  ACTUALIZAR — PUT /api/peliculas/{id}
    // ════════════════════════════════════════════════════
    public PeliculaResponseDTO actualizar(Long id, PeliculaRequestDTO dto) {
        ModeloPelicula pelicula = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Película con ID " + id + " no encontrada"));

        validarDuracion(dto.getDuracion());
        validarCategoria(dto.getCategoriaId());

        pelicula.setTitulo(dto.getTitulo());
        pelicula.setGenero(dto.getGenero());
        pelicula.setDuracion(dto.getDuracion());
        pelicula.setCategoriaId(dto.getCategoriaId());

        return mapToDTO(repository.save(pelicula));
    }

    // ════════════════════════════════════════════════════
    //  ELIMINAR — DELETE /api/peliculas/{id}
    // ════════════════════════════════════════════════════
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Película con ID " + id + " no encontrada");
        }
        repository.deleteById(id);
    }
}
