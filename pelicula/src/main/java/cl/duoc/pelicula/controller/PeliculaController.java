package cl.duoc.pelicula.controller;

import cl.duoc.pelicula.dto.PeliculaRequestDTO;
import cl.duoc.pelicula.dto.PeliculaResponseDTO;
import cl.duoc.pelicula.service.PeliculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
@RequiredArgsConstructor
public class PeliculaController {

    private final PeliculaService service;

    @GetMapping
    public ResponseEntity<List<PeliculaResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    @PostMapping
    public ResponseEntity<PeliculaResponseDTO> crear(
            @Valid @RequestBody
            PeliculaRequestDTO dto) {

        return ResponseEntity.status(201)
                .body(service.guardar(dto));
    }
}