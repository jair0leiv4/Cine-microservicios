package cl.duoc.pago.controller;

import cl.duoc.pago.dto.*;
import cl.duoc.pago.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService service;

    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>>
    listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    @PostMapping
    public ResponseEntity<PagoResponseDTO>
    crear(
            @Valid @RequestBody
            PagoRequestDTO dto) {

        return ResponseEntity.status(201)
                .body(service.guardar(dto));
    }
}