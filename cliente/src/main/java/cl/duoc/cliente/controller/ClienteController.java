package cl.duoc.cliente.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.cliente.dto.ClienteRequestDTO;
import cl.duoc.cliente.dto.ClienteResponseDTO;
import cl.duoc.cliente.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "CRUD de clientes del sistema de cine")
public class ClienteController {

    private final ClienteService service;

    // ==================================================
    // GET /api/clientes
    // ==================================================

    @Operation(summary = "Listar todos los clientes",
               description = "Retorna la lista completa de clientes registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                 content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ==================================================
    // GET /api/clientes/{id}
    // ==================================================

    @Operation(summary = "Buscar cliente por ID",
               description = "Retorna los datos de un cliente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                     content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "El cliente no existe", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // ==================================================
    // POST /api/clientes
    // ==================================================

    @Operation(summary = "Crear un nuevo cliente",
               description = "Registra un nuevo cliente con nombre, correo y teléfono")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente creado correctamente",
                     content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> guardar(@Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    // ==================================================
    // PUT /api/clientes/{id}
    // ==================================================

    @Operation(summary = "Actualizar cliente existente",
               description = "Modifica los datos de un cliente identificado por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente",
                     content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "El cliente no existe", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    // ==================================================
    // DELETE /api/clientes/{id}
    // ==================================================

    @Operation(summary = "Eliminar cliente",
               description = "Elimina un cliente del sistema por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
        @ApiResponse(responseCode = "400", description = "El cliente no existe", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Cliente eliminado correctamente");
    }
}
