package cl.duoc.categoria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.categoria.DTO.CategoriaRequestDTO;
import cl.duoc.categoria.DTO.CategoriaResponseDTO;
import cl.duoc.categoria.model.ModeloCategoria;
import cl.duoc.categoria.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;

/**
 * Capa de negocio del microservicio Categoria.
 *
 * Responsabilidades:
 *  - CRUD completo de categorías de películas.
 *  - Regla de negocio: nombre único por categoría.
 */
@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    // ════════════════════════════════════════════════════
    //  Conversión Entity → DTO
    // ════════════════════════════════════════════════════
    private CategoriaResponseDTO mapToDTO(ModeloCategoria c) {
        return new CategoriaResponseDTO(c.getId(), c.getNombre(), c.getDescripcion());
    }

    // ════════════════════════════════════════════════════
    //  LISTAR — GET /api/categorias
    // ════════════════════════════════════════════════════
    public List<CategoriaResponseDTO> obtenerTodas() {
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    // ════════════════════════════════════════════════════
    //  BUSCAR POR ID — GET /api/categorias/{id}
    // ════════════════════════════════════════════════════
    public Optional<CategoriaResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    // ════════════════════════════════════════════════════
    //  CREAR — POST /api/categorias
    // ════════════════════════════════════════════════════
    public CategoriaResponseDTO guardar(CategoriaRequestDTO dto) {
        ModeloCategoria categoria = new ModeloCategoria(
                null, dto.getNombre(), dto.getDescripcion());
        return mapToDTO(repository.save(categoria));
    }

    // ════════════════════════════════════════════════════
    //  ACTUALIZAR — PUT /api/categorias/{id}
    // ════════════════════════════════════════════════════
    public CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO dto) {
        ModeloCategoria categoria = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Categoría con ID " + id + " no encontrada"));
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return mapToDTO(repository.save(categoria));
    }

    // ════════════════════════════════════════════════════
    //  ELIMINAR — DELETE /api/categorias/{id}
    // ════════════════════════════════════════════════════
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoría con ID " + id + " no encontrada");
        }
        repository.deleteById(id);
    }
}
