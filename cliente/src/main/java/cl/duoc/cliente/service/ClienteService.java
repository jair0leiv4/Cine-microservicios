package cl.duoc.cliente.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.cliente.dto.ClienteRequestDTO;
import cl.duoc.cliente.dto.ClienteResponseDTO;
import cl.duoc.cliente.model.ModeloCliente;
import cl.duoc.cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    // CONVERTIR ENTITY A DTO
    private ClienteResponseDTO mapToDTO(
            ModeloCliente cliente) {

        ClienteResponseDTO dto =
                new ClienteResponseDTO();

        dto.setId(
                cliente.getId()
        );

        dto.setNombre(
                cliente.getNombre()
        );

        dto.setCorreo(
                cliente.getCorreo()
        );

        return dto;
    }

    // LISTAR CLIENTES
    public List<ClienteResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // BUSCAR CLIENTE POR ID
    public ClienteResponseDTO buscarPorId(Long id) {

        ModeloCliente cliente = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "El cliente no existe"
                        ));

        return mapToDTO(cliente);
    }

    // CREAR CLIENTE
    public ClienteResponseDTO guardar(
            ClienteRequestDTO dto) {

        ModeloCliente cliente =
                new ModeloCliente();

        cliente.setNombre(
                dto.getNombre()
        );

        cliente.setCorreo(
                dto.getCorreo()
        );

        return mapToDTO(
                repository.save(cliente)
        );
    }

    // ACTUALIZAR CLIENTE
    public ClienteResponseDTO actualizar(
            Long id,
            ClienteRequestDTO dto) {

        ModeloCliente cliente = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "El cliente no existe"
                        ));

        cliente.setNombre(
                dto.getNombre()
        );

        cliente.setCorreo(
                dto.getCorreo()
        );

        return mapToDTO(
                repository.save(cliente)
        );
    }

    // ELIMINAR CLIENTE
    public void eliminar(Long id) {

        if (!repository.existsById(id)) {

            throw new RuntimeException(
                    "El cliente no existe"
            );
        }

        repository.deleteById(id);
    }
}