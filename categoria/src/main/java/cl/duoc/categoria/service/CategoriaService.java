package cl.duoc.categoria.service;

import cl.duoc.categoria.DTO.CategoriaRequestDTO;
import cl.duoc.categoria.DTO.CategoriaResponseDTO;
import cl.duoc.categoria.model.ModeloCategoria;
import cl.duoc.categoria.repository.CategoriaRepository;
import lombok.Data;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Data
@Service
@RequiredArgsConstructor

public class CategoriaService {

    private final CategoriaRepository repository;

    private CategoriaResponseDTO mapToDTO(ModeloCategoria categoria) {

        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }

    public List<CategoriaResponseDTO> obtenerTodas() {

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public Optional<CategoriaResponseDTO> obtenerPorId(Long id) {

        return repository.findById(id)
                .map(this::mapToDTO);
    }

    public CategoriaResponseDTO guardar(CategoriaRequestDTO dto) {

            ModeloCategoria categoria = new ModeloCategoria(
                null,
                dto.getNombre(),
                dto.getDescripcion()
        );

        return mapToDTO(repository.save(categoria));
    }

    public void eliminar(Long id) {

        repository.deleteById(id);
    }
}