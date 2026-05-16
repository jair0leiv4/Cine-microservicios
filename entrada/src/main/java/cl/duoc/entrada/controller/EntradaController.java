package cl.duoc.entrada.controller;

import cl.duoc.entrada.dto.EntradaRequestDTO;
import cl.duoc.entrada.dto.EntradaResponseDTO;
import cl.duoc.entrada.service.EntradaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entradas")
@RequiredArgsConstructor
public class EntradaController {

    private final EntradaService service;

    @GetMapping
    public ResponseEntity<List<EntradaResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    @PostMapping
    public ResponseEntity<EntradaResponseDTO> crear(
            @Valid @RequestBody
            EntradaRequestDTO dto) {

        return ResponseEntity.status(201)
                .body(service.guardar(dto));
    }
}