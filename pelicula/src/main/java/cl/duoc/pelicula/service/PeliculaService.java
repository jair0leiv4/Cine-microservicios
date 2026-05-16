package cl.duoc.pelicula.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.duoc.pelicula.dto.PeliculaRequestDTO;
import cl.duoc.pelicula.dto.PeliculaResponseDTO;
import cl.duoc.pelicula.model.ModeloPelicula;
import cl.duoc.pelicula.repository.PeliculaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PeliculaService {

    private final PeliculaRepository repository;
    private final RestTemplate restTemplate;

    private PeliculaResponseDTO mapToDTO(ModeloPelicula pelicula) {

        return new PeliculaResponseDTO(
                pelicula.getId(),
                pelicula.getTitulo(),
                pelicula.getGenero(),
                pelicula.getDuracion(),
                pelicula.getCategoriaId()
        );
    }

    public List<PeliculaResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public PeliculaResponseDTO guardar(
            PeliculaRequestDTO dto) {

        String url =
                "http://localhost:8081/api/categorias/"
                        + dto.getCategoriaId();

        try {

            ResponseEntity<String> response =
                    restTemplate.getForEntity(
                            url,
                            String.class
                    );

            if(!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException(
                        "Categoria no encontrada"
                );
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error: categoria no existe"
            );
        }

        ModeloPelicula pelicula =
                new ModeloPelicula(
                        null,
                        dto.getTitulo(),
                        dto.getGenero(),
                        dto.getDuracion(),
                        dto.getCategoriaId()
                );

        return mapToDTO(
                repository.save(pelicula)
        );
    }
}