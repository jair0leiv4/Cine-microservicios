package cl.duoc.entrada.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.entrada.dto.EntradaRequestDTO;
import cl.duoc.entrada.dto.EntradaResponseDTO;
import cl.duoc.entrada.model.ModeloEntrada;
import cl.duoc.entrada.repository.EntradaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntradaService {

    private final EntradaRepository repository;

    // CONVERTIR ENTITY A DTO
    private EntradaResponseDTO mapToDTO(
            ModeloEntrada entrada) {

        return new EntradaResponseDTO(
                entrada.getId(),
                entrada.getTipo(),
                entrada.getPrecio(),
                entrada.getFuncionId()
        );
    }

    // LISTAR TODAS
    public List<EntradaResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // BUSCAR POR ID
    public EntradaResponseDTO buscarPorId(Long id) {

        ModeloEntrada entrada = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "La entrada no existe"
                        ));

        return mapToDTO(entrada);
    }

    // CREAR
    public EntradaResponseDTO guardar(
            EntradaRequestDTO dto) {

        ModeloEntrada entrada =
                new ModeloEntrada(
                        null,
                        dto.getTipo(),
                        dto.getPrecio(),
                        dto.getFuncionId()
                );

        return mapToDTO(
                repository.save(entrada)
        );
    }

    // ACTUALIZAR
    public EntradaResponseDTO actualizar(
            Long id,
            EntradaRequestDTO dto) {

        ModeloEntrada entrada = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "La entrada no existe"
                        ));

        entrada.setTipo(
                dto.getTipo()
        );

        entrada.setPrecio(
                dto.getPrecio()
        );

        entrada.setFuncionId(
                dto.getFuncionId()
        );

        return mapToDTO(
                repository.save(entrada)
        );
    }

    // ELIMINAR
    public void eliminar(Long id) {

        if (!repository.existsById(id)) {

            throw new RuntimeException(
                    "La entrada no existe"
            );
        }

        repository.deleteById(id);
    }
}