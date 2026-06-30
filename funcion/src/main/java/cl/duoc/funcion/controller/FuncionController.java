package cl.duoc.funcion.controller;

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

import cl.duoc.funcion.dto.FuncionRequestDTO;
import cl.duoc.funcion.dto.FuncionResponseDTO;
import cl.duoc.funcion.service.FuncionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/funciones")
@RequiredArgsConstructor
@Tag(name = "Funciones", description = "Gestión de funciones de cine — hora en formato HH:mm")
public class FuncionController {

    private final FuncionService service;

    @Operation(summary = "Listar todas las funciones")
    @ApiResponse(responseCode = "200", description = "Lista retornada correctamente",
                 content = @Content(schema = @Schema(implementation = FuncionResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<FuncionResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar función por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Función encontrada",
                     content = @Content(schema = @Schema(implementation = FuncionResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Función no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<FuncionResponseDTO> buscar(
            @Parameter(description = "ID de la función") @PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva función",
               description = "Hora debe tener formato HH:mm, ej: 18:30")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Función creada correctamente",
                     content = @Content(schema = @Schema(implementation = FuncionResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<FuncionResponseDTO> crear(@Valid @RequestBody FuncionRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    @Operation(summary = "Actualizar función existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Función actualizada correctamente",
                     content = @Content(schema = @Schema(implementation = FuncionResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Función no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<FuncionResponseDTO> actualizar(
            @Parameter(description = "ID de la función a actualizar") @PathVariable Long id,
            @Valid @RequestBody FuncionRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar función")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Función eliminada correctamente"),
        @ApiResponse(responseCode = "400", description = "Función no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID de la función a eliminar") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Función eliminada correctamente");
    }
}
