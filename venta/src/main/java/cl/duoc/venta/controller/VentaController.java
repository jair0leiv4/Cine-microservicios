package cl.duoc.venta.controller;

import cl.duoc.venta.dto.VentaRequestDTO;
import cl.duoc.venta.dto.VentaResponseDTO;
import cl.duoc.venta.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Tag(name = "Ventas", description = "CRUD de ventas del sistema de cine")
public class VentaController {

    private final VentaService service;

    // ==================================================
    // GET /api/ventas — Listar todas las ventas
    // ==================================================

    @Operation(summary = "Listar todas las ventas",
               description = "Retorna la lista completa de ventas registradas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                 content = @Content(schema = @Schema(implementation = VentaResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ==================================================
    // GET /api/ventas/{id} — Buscar venta por ID
    // ==================================================

    @Operation(summary = "Buscar una venta por ID",
               description = "Retorna los datos de una venta específica con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta encontrada",
                     content = @Content(schema = @Schema(implementation = VentaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "La venta no existe", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> buscarPorId(@PathVariable Long id) {

        VentaResponseDTO venta = service.buscarPorId(id);

        venta.add(linkTo(VentaController.class).slash(id).withSelfRel());
        venta.add(linkTo(VentaController.class).withRel("todas-las-ventas"));

        return ResponseEntity.ok(venta);
    }

    // ==================================================
    // POST /api/ventas — Crear nueva venta
    // ==================================================

    @Operation(summary = "Crear una nueva venta",
               description = "Valida cliente y entrada antes de registrar la venta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Venta creada correctamente",
                     content = @Content(schema = @Schema(implementation = VentaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o cliente/entrada inexistente",
                     content = @Content)
    })
    @PostMapping
    public ResponseEntity<VentaResponseDTO> guardar(@Valid @RequestBody VentaRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    // ==================================================
    // PUT /api/ventas/{id} — Actualizar venta
    // ==================================================

    @Operation(summary = "Actualizar una venta existente",
               description = "Actualiza los datos de una venta identificada por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta actualizada correctamente",
                     content = @Content(schema = @Schema(implementation = VentaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "La venta no existe", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VentaRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    // ==================================================
    // DELETE /api/ventas/{id} — Eliminar venta
    // ==================================================

    @Operation(summary = "Eliminar una venta",
               description = "Elimina permanentemente una venta del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta eliminada correctamente"),
        @ApiResponse(responseCode = "400", description = "La venta no existe", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Venta eliminada correctamente");
    }
}
