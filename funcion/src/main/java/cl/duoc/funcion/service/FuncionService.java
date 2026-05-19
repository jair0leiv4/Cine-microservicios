package cl.duoc.funcion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.funcion.dto.FuncionRequestDTO;
import cl.duoc.funcion.dto.FuncionResponseDTO;
import cl.duoc.funcion.model.ModeloFuncion;
import cl.duoc.funcion.repository.FuncionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FuncionService {

    private final FuncionRepository repository;

    private FuncionResponseDTO mapToDTO(
            ModeloFuncion funcion) {

        return new FuncionResponseDTO(
                funcion.getId(),
                funcion.getHora(),
                funcion.getSalaId(),
                funcion.getPeliculaId()
        );
    }

    public List<FuncionResponseDTO> listar() {

        log.info("Listando funciones");

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public Optional<FuncionResponseDTO>
    buscarPorId(Long id) {

        log.info("Buscando funcion por ID");

        return repository.findById(id)
                .map(this::mapToDTO);
    }

    public FuncionResponseDTO guardar(
            FuncionRequestDTO dto) {

        ModeloFuncion funcion =
                new ModeloFuncion(
                        null,
                        dto.getHora(),
                        dto.getSalaId(),
                        dto.getPeliculaId()
                );

        log.info("Guardando funcion");

        return mapToDTO(
                repository.save(funcion)
        );
    }

    public void eliminar(Long id) {

        log.info("Eliminando funcion");

        repository.deleteById(id);
    }
}