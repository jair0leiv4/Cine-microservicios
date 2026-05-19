package cl.duoc.producto.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.producto.dto.ProductoRequestDTO;
import cl.duoc.producto.dto.ProductoResponseDTO;
import cl.duoc.producto.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>>
    listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO>
    buscar(@PathVariable Long id) {

        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO>
    crear(
            @Valid @RequestBody
            ProductoRequestDTO dto) {

        return ResponseEntity.status(201)
                .body(service.guardar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}