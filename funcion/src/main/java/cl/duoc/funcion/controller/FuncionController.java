package cl.duoc.funcion.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.funcion.dto.FuncionRequestDTO;
import cl.duoc.funcion.dto.FuncionResponseDTO;
import cl.duoc.funcion.service.FuncionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/funciones")
@RequiredArgsConstructor
public class FuncionController {

    private final FuncionService service;

    @GetMapping
    public ResponseEntity<List<FuncionResponseDTO>>
    listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionResponseDTO>
    buscar(@PathVariable Long id) {

        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FuncionResponseDTO>
    crear(
            @Valid @RequestBody
            FuncionRequestDTO dto) {

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