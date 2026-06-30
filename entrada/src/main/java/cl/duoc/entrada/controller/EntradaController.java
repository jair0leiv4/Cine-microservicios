package cl.duoc.entrada.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.entrada.dto.EntradaRequestDTO;
import cl.duoc.entrada.dto.EntradaResponseDTO;
import cl.duoc.entrada.service.EntradaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/entradas")
@RequiredArgsConstructor
@Tag(name = "Entradas", description = "CRUD de entradas del sistema de cine")
public class EntradaController {

    private final EntradaService service;

    // ==================================================
    // GET /api/entradas
    // ==================================================

    @Operation(summary = "Listar todas las entradas",
               description = "Retorna la lista completa de entradas disponibles")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                 content = @Content(schema = @Schema(implementation = EntradaResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<EntradaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ==================================================
    // GET /api/entradas/{id}
    // ==================================================

    @Operation(summary = "Buscar entrada por ID",
               description = "Retorna los datos de una entrada específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entrada encontrada",
                     content = @Content(schema = @Schema(implementation = EntradaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "La entrada no existe", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntradaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // ==================================================
    // POST /api/entradas
    // ==================================================

    @Operation(summary = "Crear una nueva entrada",
               description = "Registra una entrada con tipo, precio y función asociada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Entrada creada correctamente",
                     content = @Content(schema = @Schema(implementation = EntradaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntradaResponseDTO> guardar(@Valid @RequestBody EntradaRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    // ==================================================
    // PUT /api/entradas/{id}
    // ==================================================

    @Operation(summary = "Actualizar entrada existente",
               description = "Modifica el tipo, precio o función de una entrada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entrada actualizada correctamente",
                     content = @Content(schema = @Schema(implementation = EntradaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "La entrada no existe", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntradaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EntradaRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    // ==================================================
    // DELETE /api/entradas/{id}
    // ==================================================

    @Operation(summary = "Eliminar entrada",
               description = "Elimina permanentemente una entrada del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entrada eliminada correctamente"),
        @ApiResponse(responseCode = "400", description = "La entrada no existe", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Entrada eliminada correctamente");
    }
}
