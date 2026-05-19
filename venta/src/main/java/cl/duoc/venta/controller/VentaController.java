package cl.duoc.venta.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.venta.dto.VentaRequestDTO;
import cl.duoc.venta.dto.VentaResponseDTO;
import cl.duoc.venta.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService service;

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>>
    listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    @PostMapping
    public ResponseEntity<VentaResponseDTO>
    crear(
            @Valid @RequestBody
            VentaRequestDTO dto) {

        return ResponseEntity.status(201)
                .body(service.guardar(dto));
    }
}