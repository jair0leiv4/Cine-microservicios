package cl.duoc.sala.controller;

import cl.duoc.sala.dto.SalaRequestDTO;
import cl.duoc.sala.dto.SalaResponseDTO;
import cl.duoc.sala.service.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService service;

    @GetMapping
    public ResponseEntity<List<SalaResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaResponseDTO>
    buscar(@PathVariable Long id) {

        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SalaResponseDTO> crear(
            @Valid @RequestBody
            SalaRequestDTO dto) {

        return ResponseEntity.status(201)
                .body(service.guardar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}