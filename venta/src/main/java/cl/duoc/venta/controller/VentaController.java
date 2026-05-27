package cl.duoc.venta.controller;

import cl.duoc.venta.dto.VentaRequestDTO;
import cl.duoc.venta.dto.VentaResponseDTO;
import cl.duoc.venta.service.VentaService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService service;

    // LISTAR TODAS LAS VENTAS
    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar());
    }

    // BUSCAR VENTA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id));
    }

    // CREAR VENTA
    @PostMapping
    public ResponseEntity<VentaResponseDTO> guardar(
            @Valid @RequestBody VentaRequestDTO dto) {

        return ResponseEntity
                .status(201)
                .body(service.guardar(dto));
    }

    // ACTUALIZAR VENTA
    @PutMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VentaRequestDTO dto) {

        return ResponseEntity.ok(
                service.actualizar(id, dto));
    }

    // ELIMINAR VENTA
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                "Venta eliminada correctamente");
    }
}