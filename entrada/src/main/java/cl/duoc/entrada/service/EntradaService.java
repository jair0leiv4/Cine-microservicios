package cl.duoc.entrada.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.duoc.entrada.dto.EntradaRequestDTO;
import cl.duoc.entrada.dto.EntradaResponseDTO;
import cl.duoc.entrada.model.ModeloEntrada;
import cl.duoc.entrada.repository.EntradaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntradaService {

    private final EntradaRepository repository;

    private final RestTemplate restTemplate;

    private EntradaResponseDTO mapToDTO(
            ModeloEntrada entrada) {

        return new EntradaResponseDTO(
                entrada.getId(),
                entrada.getTipo(),
                entrada.getPrecio(),
                entrada.getFuncionId()
        );
    }

    public List<EntradaResponseDTO> listar() {

        log.info("Listando entradas");

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public Optional<EntradaResponseDTO>
    buscarPorId(Long id) {

        log.info("Buscando entrada");

        return repository.findById(id)
                .map(this::mapToDTO);
    }




    public EntradaResponseDTO guardar(
            EntradaRequestDTO dto) {

        String funcionURL =
        "http://localhost:8085/api/funciones/"
                + dto.getFuncionId();

        try {

                restTemplate.getForEntity(
                             funcionURL,
                String.class
                );

                } catch (Exception e) {

                log.error("Funcion no encontrada");

                throw new RuntimeException(
                        "La funcion no existe"
                );
        }

        ModeloEntrada entrada =
                new ModeloEntrada(
                        null,
                        dto.getTipo(),
                        dto.getPrecio(),
                        dto.getFuncionId()
                );

        log.info("Guardando entrada");

        return mapToDTO(
                repository.save(entrada)
        );
    }



    
    public void eliminar(Long id) {

        log.info("Eliminando entrada");

        repository.deleteById(id);
    }
}