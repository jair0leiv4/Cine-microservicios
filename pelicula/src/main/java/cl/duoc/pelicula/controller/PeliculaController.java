package cl.duoc.pelicula.controller;

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

import cl.duoc.pelicula.dto.PeliculaRequestDTO;
import cl.duoc.pelicula.dto.PeliculaResponseDTO;
import cl.duoc.pelicula.service.PeliculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST de Pelicula.
 * CRUD completo en /api/peliculas.
 */
@RestController
@RequestMapping("/api/peliculas")
@RequiredArgsConstructor
@Tag(name = "Peliculas", description = "Gestión de películas en cartelera")
public class PeliculaController {

    private final PeliculaService service;

    // ─── GET /api/peliculas ──────────────────────────────
    @Operation(summary = "Listar todas las películas")
    @ApiResponse(responseCode = "200", description = "Lista retornada correctamente",
                 content = @Content(schema = @Schema(implementation = PeliculaResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<PeliculaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ─── GET /api/peliculas/{id} ─────────────────────────
    @Operation(summary = "Buscar película por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Película encontrada",
                     content = @Content(schema = @Schema(implementation = PeliculaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Película no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PeliculaResponseDTO> buscarPorId(
            @Parameter(description = "ID de la película") @PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ─── POST /api/peliculas ─────────────────────────────
    @Operation(summary = "Crear una nueva película",
               description = "Valida categoría existente y duración mínima de 1 min")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Película creada correctamente",
                     content = @Content(schema = @Schema(implementation = PeliculaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o categoría inexistente",
                     content = @Content)
    })
    @PostMapping
    public ResponseEntity<PeliculaResponseDTO> crear(
            @Valid @RequestBody PeliculaRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    // ─── PUT /api/peliculas/{id} ─────────────────────────
    @Operation(summary = "Actualizar una película existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Película actualizada correctamente",
                     content = @Content(schema = @Schema(implementation = PeliculaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Película no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PeliculaResponseDTO> actualizar(
            @Parameter(description = "ID de la película a actualizar") @PathVariable Long id,
            @Valid @RequestBody PeliculaRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    // ─── DELETE /api/peliculas/{id} ──────────────────────
    @Operation(summary = "Eliminar una película")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Película eliminada correctamente"),
        @ApiResponse(responseCode = "400", description = "Película no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID de la película a eliminar") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Película eliminada correctamente");
    }
}
