package cl.duoc.cliente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.cliente.dto.ClienteRequestDTO;
import cl.duoc.cliente.dto.ClienteResponseDTO;
import cl.duoc.cliente.model.ModeloCliente;
import cl.duoc.cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {

    private final ClienteRepository repository;

    private ClienteResponseDTO mapToDTO(
            ModeloCliente cliente) {

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getCorreo(),
                cliente.getTelefono()
        );
    }

    public List<ClienteResponseDTO> listar() {

        log.info("Listando clientes");

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public Optional<ClienteResponseDTO>
    buscarPorId(Long id) {

        log.info("Buscando cliente por ID");

        return repository.findById(id)
                .map(this::mapToDTO);
    }

    public ClienteResponseDTO guardar(
            ClienteRequestDTO dto) {

        ModeloCliente cliente =
                new ModeloCliente(
                        null,
                        dto.getNombre(),
                        dto.getCorreo(),
                        dto.getTelefono()
                );

        log.info("Guardando cliente");

        return mapToDTO(
                repository.save(cliente)
        );
    }

    public void eliminar(Long id) {

        log.info("Eliminando cliente");

        repository.deleteById(id);
    }
}