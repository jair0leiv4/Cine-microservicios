package cl.duoc.cliente.controller;

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

import cl.duoc.cliente.dto.ClienteRequestDTO;
import cl.duoc.cliente.dto.ClienteResponseDTO;
import cl.duoc.cliente.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    // LISTAR CLIENTES
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    // BUSCAR CLIENTE POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    // CREAR CLIENTE
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> guardar(
            @Valid @RequestBody ClienteRequestDTO dto) {

        return ResponseEntity
                .status(201)
                .body(service.guardar(dto));
    }

    // ACTUALIZAR CLIENTE
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO dto) {

        return ResponseEntity.ok(
                service.actualizar(id, dto)
        );
    }

    // ELIMINAR CLIENTE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                "Cliente eliminado correctamente"
        );
    }
}