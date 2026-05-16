package cl.duoc.entrada.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.duoc.entrada.dto.EntradaRequestDTO;
import cl.duoc.entrada.dto.EntradaResponseDTO;
import cl.duoc.entrada.model.ModeloEntrada;
import cl.duoc.entrada.repository.EntradaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntradaService {

    private final EntradaRepository repository;
    private final RestTemplate restTemplate;

    private EntradaResponseDTO mapToDTO(
            ModeloEntrada entrada) {

        return new EntradaResponseDTO(
                entrada.getId(),
                entrada.getFuncionId(),
                entrada.getAsientoId(),
                entrada.getPrecio()
        );
    }

    public List<EntradaResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public EntradaResponseDTO guardar(
            EntradaRequestDTO dto) {

        String funcionURL =
                "http://localhost:8085/api/funciones/"
                        + dto.getFuncionId();

        String asientoURL =
                "http://localhost:8084/api/asientos/"
                        + dto.getAsientoId();

        try {

            ResponseEntity<String> funcionResponse =
                    restTemplate.getForEntity(
                            funcionURL,
                            String.class
                    );

            ResponseEntity<String> asientoResponse =
                    restTemplate.getForEntity(
                            asientoURL,
                            String.class
                    );

            if(!funcionResponse
                    .getStatusCode()
                    .is2xxSuccessful()) {

                throw new RuntimeException(
                        "Funcion no encontrada"
                );
            }

            if(!asientoResponse
                    .getStatusCode()
                    .is2xxSuccessful()) {

                throw new RuntimeException(
                        "Asiento no encontrado"
                );
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error validando funcion o asiento"
            );
        }

        ModeloEntrada entrada =
                new ModeloEntrada(
                        null,
                        dto.getFuncionId(),
                        dto.getAsientoId(),
                        dto.getPrecio()
                );

        return mapToDTO(
                repository.save(entrada)
        );
    }
}