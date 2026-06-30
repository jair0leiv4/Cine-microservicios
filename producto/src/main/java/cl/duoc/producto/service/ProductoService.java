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

/**
 * Capa de negocio del microservicio Producto.
 *
 * Responsabilidades:
 *  - CRUD completo de productos de concesión.
 *  - Reglas de negocio: precio mínimo $1, stock no negativo.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoService {

    private final ProductoRepository repository;

    // ════════════════════════════════════════════════════
    //  Conversión Entity → DTO
    // ════════════════════════════════════════════════════
    private ProductoResponseDTO mapToDTO(ModeloProducto p) {
        return new ProductoResponseDTO(p.getId(), p.getNombre(), p.getStock(), p.getPrecio());
    }

    // ─── Reglas de negocio ───────────────────────────────
    private void validarPrecio(Integer precio) {
        if (precio == null || precio < 1) {
            throw new RuntimeException("El precio debe ser al menos 1");
        }
    }

    private void validarStock(Integer stock) {
        if (stock == null || stock < 0) {
            throw new RuntimeException("El stock no puede ser negativo");
        }
    }

    // ════════════════════════════════════════════════════
    //  LISTAR — GET /api/productos
    // ════════════════════════════════════════════════════
    public List<ProductoResponseDTO> listar() {
        log.info("Listando productos");
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    // ════════════════════════════════════════════════════
    //  BUSCAR POR ID — GET /api/productos/{id}
    // ════════════════════════════════════════════════════
    public Optional<ProductoResponseDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    // ════════════════════════════════════════════════════
    //  CREAR — POST /api/productos
    // ════════════════════════════════════════════════════
    public ProductoResponseDTO guardar(ProductoRequestDTO dto) {
        validarPrecio(dto.getPrecio());
        validarStock(dto.getStock());
        ModeloProducto producto = new ModeloProducto(
                null, dto.getNombre(), dto.getStock(), dto.getPrecio());
        log.info("Guardando producto: {}", dto.getNombre());
        return mapToDTO(repository.save(producto));
    }

    // ════════════════════════════════════════════════════
    //  ACTUALIZAR — PUT /api/productos/{id}
    // ════════════════════════════════════════════════════
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) {
        ModeloProducto producto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Producto con ID " + id + " no encontrado"));
        validarPrecio(dto.getPrecio());
        validarStock(dto.getStock());
        producto.setNombre(dto.getNombre());
        producto.setStock(dto.getStock());
        producto.setPrecio(dto.getPrecio());
        return mapToDTO(repository.save(producto));
    }

    // ════════════════════════════════════════════════════
    //  ELIMINAR — DELETE /api/productos/{id}
    // ════════════════════════════════════════════════════
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Producto con ID " + id + " no encontrado");
        }
        repository.deleteById(id);
    }
}
