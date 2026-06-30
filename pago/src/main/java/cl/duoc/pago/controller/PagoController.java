package cl.duoc.pago.controller;

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

import cl.duoc.pago.dto.PagoRequestDTO;
import cl.duoc.pago.dto.PagoResponseDTO;
import cl.duoc.pago.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST de Pago.
 * CRUD completo en /api/pagos.
 */
@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "Gestión de pagos de ventas — métodos: TARJETA, EFECTIVO, TRANSFERENCIA")
public class PagoController {

    private final PagoService service;

    // ─── GET /api/pagos ──────────────────────────────────
    @Operation(summary = "Listar todos los pagos")
    @ApiResponse(responseCode = "200", description = "Lista retornada correctamente",
                 content = @Content(schema = @Schema(implementation = PagoResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ─── GET /api/pagos/{id} ─────────────────────────────
    @Operation(summary = "Buscar pago por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago encontrado",
                     content = @Content(schema = @Schema(implementation = PagoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> buscarPorId(
            @Parameter(description = "ID del pago") @PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ─── POST /api/pagos ─────────────────────────────────
    @Operation(summary = "Registrar un nuevo pago",
               description = "Valida venta existente, monto positivo y método válido")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pago registrado correctamente",
                     content = @Content(schema = @Schema(implementation = PagoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o venta inexistente",
                     content = @Content)
    })
    @PostMapping
    public ResponseEntity<PagoResponseDTO> crear(
            @Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    // ─── PUT /api/pagos/{id} ─────────────────────────────
    @Operation(summary = "Actualizar un pago existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente",
                     content = @Content(schema = @Schema(implementation = PagoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Pago no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> actualizar(
            @Parameter(description = "ID del pago a actualizar") @PathVariable Long id,
            @Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    // ─── DELETE /api/pagos/{id} ──────────────────────────
    @Operation(summary = "Eliminar un pago")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago eliminado correctamente"),
        @ApiResponse(responseCode = "400", description = "Pago no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID del pago a eliminar") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Pago eliminado correctamente");
    }
}
