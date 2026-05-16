package cl.duoc.asiento.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.duoc.asiento.dto.AsientoRequestDTO;
import cl.duoc.asiento.dto.AsientoResponseDTO;
import cl.duoc.asiento.model.ModeloAsiento;
import cl.duoc.asiento.repository.AsientoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsientoService {

    private final AsientoRepository repository;
    private final RestTemplate restTemplate;

    private AsientoResponseDTO mapToDTO(
            ModeloAsiento asiento) {

        return new AsientoResponseDTO(
                asiento.getId(),
                asiento.getNumero(),
                asiento.getSalaId()
        );
    }

    public List<AsientoResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public AsientoResponseDTO guardar(
            AsientoRequestDTO dto) {

        String salaURL =
                "http://localhost:8083/api/salas/"
                        + dto.getSalaId();

        try {

            ResponseEntity<String> response =
                    restTemplate.getForEntity(
                            salaURL,
                            String.class
                    );

            if(!response
                    .getStatusCode()
                    .is2xxSuccessful()) {

                throw new RuntimeException(
                        "Sala no encontrada"
                );
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error: sala no existe"
            );
        }

        ModeloAsiento asiento =
                new ModeloAsiento(
                        null,
                        dto.getNumero(),
                        dto.getSalaId()
                );

        return mapToDTO(
                repository.save(asiento)
        );
    }
}