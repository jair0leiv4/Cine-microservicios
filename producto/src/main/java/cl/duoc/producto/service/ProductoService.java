package cl.duoc.producto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.producto.dto.ProductoRequestDTO;
import cl.duoc.producto.dto.ProductoResponseDTO;
import cl.duoc.producto.model.ModeloProducto;
import cl.duoc.producto.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoService {

    private final ProductoRepository repository;

    private ProductoResponseDTO mapToDTO(
            ModeloProducto producto) {

        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getStock(),
                producto.getPrecio()
        );
    }

    public List<ProductoResponseDTO> listar() {

        log.info("Listando productos");

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public Optional<ProductoResponseDTO>
    buscarPorId(Long id) {

        log.info("Buscando producto por ID");

        return repository.findById(id)
                .map(this::mapToDTO);
    }

    public ProductoResponseDTO guardar(
            ProductoRequestDTO dto) {

        ModeloProducto producto =
                new ModeloProducto(
                        null,
                        dto.getNombre(),
                        dto.getStock(),
                        dto.getPrecio()
                );

        log.info("Guardando producto");

        return mapToDTO(
                repository.save(producto)
        );
    }

    public void eliminar(Long id) {

        log.info("Eliminando producto");

        repository.deleteById(id);
    }
}