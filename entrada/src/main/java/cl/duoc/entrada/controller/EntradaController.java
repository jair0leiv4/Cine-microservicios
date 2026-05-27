package cl.duoc.entrada.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.entrada.dto.EntradaRequestDTO;
import cl.duoc.entrada.dto.EntradaResponseDTO;
import cl.duoc.entrada.service.EntradaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/entradas")
@RequiredArgsConstructor
public class EntradaController {

    private final EntradaService service;

    // LISTAR TODAS
    @GetMapping
    public ResponseEntity<List<EntradaResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<EntradaResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    // CREAR
    @PostMapping
    public ResponseEntity<EntradaResponseDTO> guardar(
            @Valid @RequestBody EntradaRequestDTO dto) {

        return ResponseEntity
                .status(201)
                .body(service.guardar(dto));
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<EntradaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EntradaRequestDTO dto) {

        return ResponseEntity.ok(
                service.actualizar(id, dto)
        );
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                "Entrada eliminada correctamente"
        );
    }
}