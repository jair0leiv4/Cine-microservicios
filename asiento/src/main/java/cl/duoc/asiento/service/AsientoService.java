package cl.duoc.asiento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.duoc.asiento.dto.AsientoRequestDTO;
import cl.duoc.asiento.dto.AsientoResponseDTO;
import cl.duoc.asiento.model.ModeloAsiento;
import cl.duoc.asiento.repository.AsientoRepository;
import lombok.RequiredArgsConstructor;

/**
 * Capa de negocio del microservicio Asiento.
 *
 * Responsabilidades:
 *  - CRUD completo de asientos.
 *  - Validar que la sala referenciada exista
 *    antes de crear o actualizar un asiento
 *    (comunicación REST con microservicio Sala).
 */
@Service
@RequiredArgsConstructor
public class AsientoService {

    private final AsientoRepository repository;
    private final RestTemplate restTemplate;

    // ─── URL base del microservicio Sala ────────────────
    private static final String SALA_URL =
            "http://localhost:8083/api/salas/";

    // ════════════════════════════════════════════════════
    //  Conversión Entity → DTO de respuesta
    // ════════════════════════════════════════════════════
    private AsientoResponseDTO mapToDTO(ModeloAsiento a) {
        return new AsientoResponseDTO(a.getId(), a.getNumero(), a.getSalaId());
    }

    // ════════════════════════════════════════════════════
    //  Validación: la sala debe existir en el otro servicio
    // ════════════════════════════════════════════════════
    private void validarSala(Long salaId) {
        try {
            ResponseEntity<String> res =
                    restTemplate.getForEntity(SALA_URL + salaId, String.class);
            if (!res.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("La sala con ID " + salaId + " no existe");
            }
        } catch (Exception e) {
            throw new RuntimeException("La sala con ID " + salaId + " no existe");
        }
    }

    // ════════════════════════════════════════════════════
    //  LISTAR — GET /api/asientos
    // ════════════════════════════════════════════════════
    public List<AsientoResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ════════════════════════════════════════════════════
    //  BUSCAR POR ID — GET /api/asientos/{id}
    // ════════════════════════════════════════════════════
    public AsientoResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException(
                        "Asiento con ID " + id + " no encontrado"));
    }

    // ════════════════════════════════════════════════════
    //  CREAR — POST /api/asientos
    // ════════════════════════════════════════════════════
    public AsientoResponseDTO guardar(AsientoRequestDTO dto) {
        // Regla de negocio: la sala debe existir
        validarSala(dto.getSalaId());

        ModeloAsiento asiento = new ModeloAsiento(
                null,
                dto.getNumero(),
                dto.getSalaId());

        return mapToDTO(repository.save(asiento));
    }

    // ════════════════════════════════════════════════════
    //  ACTUALIZAR — PUT /api/asientos/{id}
    // ════════════════════════════════════════════════════
    public AsientoResponseDTO actualizar(Long id, AsientoRequestDTO dto) {
        // Verificar que el asiento existe
        ModeloAsiento asiento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Asiento con ID " + id + " no encontrado"));

        // Validar nueva sala si cambió
        validarSala(dto.getSalaId());

        asiento.setNumero(dto.getNumero());
        asiento.setSalaId(dto.getSalaId());

        return mapToDTO(repository.save(asiento));
    }

    // ════════════════════════════════════════════════════
    //  ELIMINAR — DELETE /api/asientos/{id}
    // ════════════════════════════════════════════════════
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException(
                    "Asiento con ID " + id + " no encontrado");
        }
        repository.deleteById(id);
    }
}
