package cl.duoc.asiento.controller;

import cl.duoc.asiento.dto.AsientoRequestDTO;
import cl.duoc.asiento.dto.AsientoResponseDTO;
import cl.duoc.asiento.service.AsientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asientos")
@RequiredArgsConstructor
public class AsientoController {

    private final AsientoService service;

    @GetMapping
    public ResponseEntity<List<AsientoResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    @PostMapping
    public ResponseEntity<AsientoResponseDTO> crear(
            @Valid @RequestBody
            AsientoRequestDTO dto) {

        return ResponseEntity.status(201)
                .body(service.guardar(dto));
    }
}