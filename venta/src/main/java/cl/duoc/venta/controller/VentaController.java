package cl.duoc.venta.controller;

import cl.duoc.venta.dto.VentaRequestDTO;
import cl.duoc.venta.dto.VentaResponseDTO;
import cl.duoc.venta.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Tag(name = "Ventas", description = "CRUD de ventas")
public class VentaController {

    private final VentaService service;

    // LISTAR TODAS LAS VENTAS
    @Operation(summary = "Listar todas las ventas")
    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar());
    }

    // BUSCAR VENTA POR ID
    @Operation(summary = "Buscar venta por ID")
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> buscarPorId(
            @PathVariable Long id) {

        VentaResponseDTO venta =
                service.buscarPorId(id);

        venta.add(
                linkTo(VentaController.class)
                        .slash(id)
                        .withSelfRel()
        );

        venta.add(
                linkTo(VentaController.class)
                        .withRel("todas-las-ventas")
        );

        return ResponseEntity.ok(venta);
    }

    // CREAR VENTA
    @Operation(summary = "Crear una venta")
    @PostMapping
    public ResponseEntity<VentaResponseDTO> guardar(
            @Valid @RequestBody VentaRequestDTO dto) {

        return ResponseEntity
                .status(201)
                .body(service.guardar(dto));
    }

    // ACTUALIZAR VENTA
    @Operation(summary = "Actualizar una venta")
    @PutMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VentaRequestDTO dto) {

        return ResponseEntity.ok(
                service.actualizar(id, dto));
    }

    // ELIMINAR VENTA
    @Operation(summary = "Eliminar una venta")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                "Venta eliminada correctamente");
    }
}