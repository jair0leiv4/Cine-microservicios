package cl.duoc.funcion.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.duoc.funcion.dto.FuncionRequestDTO;
import cl.duoc.funcion.dto.FuncionResponseDTO;
import cl.duoc.funcion.model.ModeloFuncion;
import cl.duoc.funcion.repository.FuncionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuncionService {

    private final FuncionRepository repository;
    private final RestTemplate restTemplate;

    private FuncionResponseDTO mapToDTO(
            ModeloFuncion funcion) {

        return new FuncionResponseDTO(
                funcion.getId(),
                funcion.getFecha(),
                funcion.getHora(),
                funcion.getPeliculaId(),
                funcion.getSalaId()
        );
    }

    public List<FuncionResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public FuncionResponseDTO guardar(
            FuncionRequestDTO dto) {

        String peliculaURL =
                "http://localhost:8082/api/peliculas/"
                        + dto.getPeliculaId();

        String salaURL =
                "http://localhost:8083/api/salas/"
                        + dto.getSalaId();

        try {

            ResponseEntity<String> peliculaResponse =
                    restTemplate.getForEntity(
                            peliculaURL,
                            String.class
                    );

            ResponseEntity<String> salaResponse =
                    restTemplate.getForEntity(
                            salaURL,
                            String.class
                    );

            if(!peliculaResponse
                    .getStatusCode()
                    .is2xxSuccessful()) {

                throw new RuntimeException(
                        "Pelicula no encontrada"
                );
            }

            if(!salaResponse
                    .getStatusCode()
                    .is2xxSuccessful()) {

                throw new RuntimeException(
                        "Sala no encontrada"
                );
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error validando pelicula o sala"
            );
        }

        ModeloFuncion funcion =
                new ModeloFuncion(
                        null,
                        dto.getFecha(),
                        dto.getHora(),
                        dto.getPeliculaId(),
                        dto.getSalaId()
                );

        return mapToDTO(
                repository.save(funcion)
        );
    }
}