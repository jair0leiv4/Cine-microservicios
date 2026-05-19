package cl.duoc.entrada.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping
    public ResponseEntity<List<EntradaResponseDTO>>
    listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaResponseDTO>
    buscar(@PathVariable Long id) {

        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntradaResponseDTO>
    crear(
            @Valid @RequestBody
            EntradaRequestDTO dto) {

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