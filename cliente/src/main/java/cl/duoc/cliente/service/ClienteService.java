package cl.duoc.cliente.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.cliente.dto.ClienteRequestDTO;
import cl.duoc.cliente.dto.ClienteResponseDTO;
import cl.duoc.cliente.model.ModeloCliente;
import cl.duoc.cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

/**
 * Capa de negocio del microservicio Cliente.
 *
 * Responsabilidades:
 *  - CRUD completo de clientes.
 *  - Mapeo correcto de todos los campos (nombre, correo, teléfono).
 */
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    // ════════════════════════════════════════════════════
    //  Conversión Entity → DTO (incluye teléfono)
    // ════════════════════════════════════════════════════
    private ClienteResponseDTO mapToDTO(ModeloCliente c) {
        return new ClienteResponseDTO(
                c.getId(), c.getNombre(), c.getCorreo(), c.getTelefono());
    }

    // ════════════════════════════════════════════════════
    //  LISTAR — GET /api/clientes
    // ════════════════════════════════════════════════════
    public List<ClienteResponseDTO> listar() {
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    // ════════════════════════════════════════════════════
    //  BUSCAR POR ID — GET /api/clientes/{id}
    // ════════════════════════════════════════════════════
    public ClienteResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException(
                        "Cliente con ID " + id + " no encontrado"));
    }

    // ════════════════════════════════════════════════════
    //  CREAR — POST /api/clientes
    // ════════════════════════════════════════════════════
    public ClienteResponseDTO guardar(ClienteRequestDTO dto) {
        ModeloCliente cliente = new ModeloCliente(
                null, dto.getNombre(), dto.getCorreo(), dto.getTelefono());
        return mapToDTO(repository.save(cliente));
    }

    // ════════════════════════════════════════════════════
    //  ACTUALIZAR — PUT /api/clientes/{id}
    // ════════════════════════════════════════════════════
    public ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto) {
        ModeloCliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Cliente con ID " + id + " no encontrado"));
        cliente.setNombre(dto.getNombre());
        cliente.setCorreo(dto.getCorreo());
        cliente.setTelefono(dto.getTelefono());
        return mapToDTO(repository.save(cliente));
    }

    // ════════════════════════════════════════════════════
    //  ELIMINAR — DELETE /api/clientes/{id}
    // ════════════════════════════════════════════════════
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cliente con ID " + id + " no encontrado");
        }
        repository.deleteById(id);
    }
}
