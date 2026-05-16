package cl.duoc.funcion.controller;

import cl.duoc.funcion.dto.FuncionRequestDTO;
import cl.duoc.funcion.dto.FuncionResponseDTO;
import cl.duoc.funcion.service.FuncionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funciones")
@RequiredArgsConstructor
public class FuncionController {

    private final FuncionService service;

    @GetMapping
    public ResponseEntity<List<FuncionResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    @PostMapping
    public ResponseEntity<FuncionResponseDTO> crear(
            @Valid @RequestBody
            FuncionRequestDTO dto) {

        return ResponseEntity.status(201)
                .body(service.guardar(dto));
    }
}