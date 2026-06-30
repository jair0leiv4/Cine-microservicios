package cl.duoc.asiento.controller;

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

import cl.duoc.asiento.dto.AsientoRequestDTO;
import cl.duoc.asiento.dto.AsientoResponseDTO;
import cl.duoc.asiento.service.AsientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST de Asiento.
 * Expone el CRUD completo en /api/asientos.
 * Delega toda la lógica al servicio.
 */
@RestController
@RequestMapping("/api/asientos")
@RequiredArgsConstructor
@Tag(name = "Asientos", description = "Gestión de asientos por sala")
public class AsientoController {

    private final AsientoService service;

    // ─── GET /api/asientos ───────────────────────────────
    @Operation(summary = "Listar todos los asientos")
    @ApiResponse(responseCode = "200", description = "Lista retornada correctamente",
                 content = @Content(schema = @Schema(implementation = AsientoResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<AsientoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ─── GET /api/asientos/{id} ──────────────────────────
    @Operation(summary = "Buscar asiento por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Asiento encontrado",
                     content = @Content(schema = @Schema(implementation = AsientoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Asiento no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<AsientoResponseDTO> buscarPorId(
            @Parameter(description = "ID del asiento") @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // ─── POST /api/asientos ──────────────────────────────
    @Operation(summary = "Crear un nuevo asiento",
               description = "Valida que la sala referenciada exista antes de crear")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Asiento creado correctamente",
                     content = @Content(schema = @Schema(implementation = AsientoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o sala inexistente",
                     content = @Content)
    })
    @PostMapping
    public ResponseEntity<AsientoResponseDTO> crear(
            @Valid @RequestBody AsientoRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    // ─── PUT /api/asientos/{id} ──────────────────────────
    @Operation(summary = "Actualizar un asiento existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Asiento actualizado correctamente",
                     content = @Content(schema = @Schema(implementation = AsientoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Asiento no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<AsientoResponseDTO> actualizar(
            @Parameter(description = "ID del asiento a actualizar") @PathVariable Long id,
            @Valid @RequestBody AsientoRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    // ─── DELETE /api/asientos/{id} ───────────────────────
    @Operation(summary = "Eliminar un asiento")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Asiento eliminado correctamente"),
        @ApiResponse(responseCode = "400", description = "Asiento no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID del asiento a eliminar") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Asiento eliminado correctamente");
    }
}
