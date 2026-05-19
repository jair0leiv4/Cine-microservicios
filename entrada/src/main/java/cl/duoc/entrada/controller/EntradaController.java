package cl.duoc.entrada.controller; // paquete controller

import java.util.List; // importar dto

import org.springframework.http.ResponseEntity; // importar service
import org.springframework.web.bind.annotation.GetMapping; // validaciones
import org.springframework.web.bind.annotation.PostMapping; // constructor automatico
import org.springframework.web.bind.annotation.RequestBody; // respuestas http
import org.springframework.web.bind.annotation.RequestMapping; // anotaciones rest
import org.springframework.web.bind.annotation.RestController; // listas

import cl.duoc.entrada.dto.EntradaRequestDTO;
import cl.duoc.entrada.dto.EntradaResponseDTO;
import cl.duoc.entrada.service.EntradaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController // controlador rest
@RequestMapping("/api/entradas") // ruta api
@RequiredArgsConstructor // constructor automatico
public class EntradaController {

    private final EntradaService service; // service

    @GetMapping // listar
    public ResponseEntity<List<EntradaResponseDTO>>
    listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    @PostMapping // crear
    public ResponseEntity<EntradaResponseDTO>
    crear(
            @Valid @RequestBody
            EntradaRequestDTO dto) {

        return ResponseEntity.status(201)
                .body(service.guardar(dto));
    }
}