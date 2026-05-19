package cl.duoc.entrada.service; // paquete service

import cl.duoc.entrada.dto.*; // importar dto
import cl.duoc.entrada.model.ModeloEntrada; // importar modelo
import cl.duoc.entrada.repository.EntradaRepository; // importar repo

import lombok.RequiredArgsConstructor; // constructor automatico
import lombok.extern.slf4j.Slf4j; // logs

import org.springframework.stereotype.Service; // service
import org.springframework.web.client.RestTemplate; // cliente http

import java.util.List; // listas

@Service // indica capa service
@RequiredArgsConstructor // constructor automatico
@Slf4j // habilita logs
public class EntradaService {

    private final EntradaRepository repository; // repositorio

    private final RestTemplate restTemplate; // conexion microservicio

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

        log.info("Listando entradas"); // log consola

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
}