package cl.duoc.funcion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.funcion.dto.FuncionRequestDTO;
import cl.duoc.funcion.dto.FuncionResponseDTO;
import cl.duoc.funcion.model.ModeloFuncion;
import cl.duoc.funcion.repository.FuncionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Capa de negocio del microservicio Funcion.
 *
 * Responsabilidades:
 *  - CRUD completo de funciones de cine.
 *  - Regla de negocio: formato de hora HH:mm (ej: 18:30).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FuncionService {

    private final FuncionRepository repository;

    // ════════════════════════════════════════════════════
    //  Conversión Entity → DTO
    // ════════════════════════════════════════════════════
    private FuncionResponseDTO mapToDTO(ModeloFuncion f) {
        return new FuncionResponseDTO(f.getId(), f.getHora(), f.getSalaId(), f.getPeliculaId());
    }

    // ─── Regla de negocio: validar formato HH:mm ─────────
    private void validarHora(String hora) {
        if (hora == null || !hora.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
            throw new RuntimeException("Hora inválida. Use formato HH:mm (ej: 18:30)");
        }
    }

    // ════════════════════════════════════════════════════
    //  LISTAR — GET /api/funciones
    // ════════════════════════════════════════════════════
    public List<FuncionResponseDTO> listar() {
        log.info("Listando funciones");
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    // ════════════════════════════════════════════════════
    //  BUSCAR POR ID — GET /api/funciones/{id}
    // ════════════════════════════════════════════════════
    public Optional<FuncionResponseDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    // ════════════════════════════════════════════════════
    //  CREAR — POST /api/funciones
    // ════════════════════════════════════════════════════
    public FuncionResponseDTO guardar(FuncionRequestDTO dto) {
        validarHora(dto.getHora());
        ModeloFuncion funcion = new ModeloFuncion(
                null, dto.getHora(), dto.getSalaId(), dto.getPeliculaId());
        log.info("Guardando función en sala {} a las {}", dto.getSalaId(), dto.getHora());
        return mapToDTO(repository.save(funcion));
    }

    // ════════════════════════════════════════════════════
    //  ACTUALIZAR — PUT /api/funciones/{id}
    // ════════════════════════════════════════════════════
    public FuncionResponseDTO actualizar(Long id, FuncionRequestDTO dto) {
        ModeloFuncion funcion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Función con ID " + id + " no encontrada"));
        validarHora(dto.getHora());
        funcion.setHora(dto.getHora());
        funcion.setSalaId(dto.getSalaId());
        funcion.setPeliculaId(dto.getPeliculaId());
        return mapToDTO(repository.save(funcion));
    }

    // ════════════════════════════════════════════════════
    //  ELIMINAR — DELETE /api/funciones/{id}
    // ════════════════════════════════════════════════════
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Función con ID " + id + " no encontrada");
        }
        repository.deleteById(id);
    }
}
