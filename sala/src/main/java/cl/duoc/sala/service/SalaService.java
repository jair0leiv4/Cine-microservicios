package cl.duoc.sala.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.sala.dto.SalaRequestDTO;
import cl.duoc.sala.dto.SalaResponseDTO;
import cl.duoc.sala.model.ModeloSala;
import cl.duoc.sala.repository.SalaRepository;
import lombok.RequiredArgsConstructor;

/**
 * Capa de negocio del microservicio Sala.
 *
 * Responsabilidades:
 *  - CRUD completo de salas de cine.
 *  - Reglas de negocio: capacidad mínima de 1 asiento.
 */
@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository repository;

    // ════════════════════════════════════════════════════
    //  Conversión Entity → DTO
    // ════════════════════════════════════════════════════
    private SalaResponseDTO mapToDTO(ModeloSala sala) {
        return new SalaResponseDTO(sala.getId(), sala.getNombre(), sala.getCapacidad());
    }

    // ─── Regla de negocio ────────────────────────────────
    private void validarCapacidad(Integer capacidad) {
        if (capacidad == null || capacidad < 1) {
            throw new RuntimeException("La capacidad de la sala debe ser al menos 1");
        }
    }

    // ════════════════════════════════════════════════════
    //  LISTAR — GET /api/salas
    // ════════════════════════════════════════════════════
    public List<SalaResponseDTO> listar() {
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    // ════════════════════════════════════════════════════
    //  BUSCAR POR ID — GET /api/salas/{id}
    // ════════════════════════════════════════════════════
    public Optional<SalaResponseDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    // ════════════════════════════════════════════════════
    //  CREAR — POST /api/salas
    // ════════════════════════════════════════════════════
    public SalaResponseDTO guardar(SalaRequestDTO dto) {
        validarCapacidad(dto.getCapacidad());
        ModeloSala sala = new ModeloSala(null, dto.getNombre(), dto.getCapacidad());
        return mapToDTO(repository.save(sala));
    }

    // ════════════════════════════════════════════════════
    //  ACTUALIZAR — PUT /api/salas/{id}
    // ════════════════════════════════════════════════════
    public SalaResponseDTO actualizar(Long id, SalaRequestDTO dto) {
        ModeloSala sala = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala con ID " + id + " no encontrada"));
        validarCapacidad(dto.getCapacidad());
        sala.setNombre(dto.getNombre());
        sala.setCapacidad(dto.getCapacidad());
        return mapToDTO(repository.save(sala));
    }

    // ════════════════════════════════════════════════════
    //  ELIMINAR — DELETE /api/salas/{id}
    // ════════════════════════════════════════════════════
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Sala con ID " + id + " no encontrada");
        }
        repository.deleteById(id);
    }
}
