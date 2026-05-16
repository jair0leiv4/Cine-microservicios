package cl.duoc.sala.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.sala.dto.SalaRequestDTO;
import cl.duoc.sala.dto.SalaResponseDTO;
import cl.duoc.sala.model.ModeloSala;
import cl.duoc.sala.repository.SalaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository repository;

    private SalaResponseDTO mapToDTO(
            ModeloSala sala) {

        return new SalaResponseDTO(
                sala.getId(),
                sala.getNombre(),
                sala.getCapacidad()
        );
    }

    public List<SalaResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public Optional<SalaResponseDTO> buscarPorId(
            Long id) {

        return repository.findById(id)
                .map(this::mapToDTO);
    }

    public SalaResponseDTO guardar(
            SalaRequestDTO dto) {

        ModeloSala sala =
                new ModeloSala(
                        null,
                        dto.getNombre(),
                        dto.getCapacidad()
                );

        return mapToDTO(
                repository.save(sala)
        );
    }

    public void eliminar(Long id) {

        repository.deleteById(id);
    }
}