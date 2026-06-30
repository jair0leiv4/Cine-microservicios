package cl.duoc.sala.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.sala.dto.SalaRequestDTO;
import cl.duoc.sala.dto.SalaResponseDTO;
import cl.duoc.sala.service.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/salas")
@RequiredArgsConstructor
@Tag(name = "Salas", description = "Gestión de salas de cine")
public class SalaController {

    private final SalaService service;

    @Operation(summary = "Listar todas las salas")
    @ApiResponse(responseCode = "200", description = "Lista retornada correctamente",
                 content = @Content(schema = @Schema(implementation = SalaResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<SalaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar sala por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sala encontrada",
                     content = @Content(schema = @Schema(implementation = SalaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Sala no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> buscarPorId(
            @Parameter(description = "ID de la sala") @PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva sala",
               description = "La capacidad debe ser al menos 1")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Sala creada correctamente",
                     content = @Content(schema = @Schema(implementation = SalaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<SalaResponseDTO> crear(@Valid @RequestBody SalaRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    @Operation(summary = "Actualizar sala existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sala actualizada correctamente",
                     content = @Content(schema = @Schema(implementation = SalaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Sala no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> actualizar(
            @Parameter(description = "ID de la sala a actualizar") @PathVariable Long id,
            @Valid @RequestBody SalaRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar sala")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sala eliminada correctamente"),
        @ApiResponse(responseCode = "400", description = "Sala no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID de la sala a eliminar") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Sala eliminada correctamente");
    }
}
