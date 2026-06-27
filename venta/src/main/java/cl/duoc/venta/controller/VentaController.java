```java
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
@Tag(
        name = "Ventas",
        description = "CRUD de ventas del sistema"
)
public class VentaController {

    private final VentaService service;

    // ==========================
    // LISTAR TODAS LAS VENTAS
    // ==========================

    @Operation(summary = "Listar todas las ventas")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista obtenida correctamente"
            )
    })

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    // ==========================
    // BUSCAR VENTA POR ID
    // ==========================

    @Operation(summary = "Buscar una venta por ID")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Venta encontrada",
                    content = @Content(
                            schema = @Schema(
                                    implementation = VentaResponseDTO.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "La venta no existe"
            )
    })

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> buscarPorId(
            @PathVariable Long id) {

        VentaResponseDTO venta =
                service.buscarPorId(id);

        venta.add(
                linkTo(VentaController.class)
                        .slash(id)
                        .withSelfRel()
        );

        venta.add(
                linkTo(VentaController.class)
                        .withRel("todas-las-ventas")
        );

        return ResponseEntity.ok(venta);
    }

    // ==========================
    // CREAR VENTA
    // ==========================

    @Operation(summary = "Crear una nueva venta")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Venta creada correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            )
    })

    @PostMapping
    public ResponseEntity<VentaResponseDTO> guardar(
            @Valid @RequestBody VentaRequestDTO dto) {

        return ResponseEntity
                .status(201)
                .body(
                        service.guardar(dto)
                );
    }

    // ==========================
    // ACTUALIZAR VENTA
    // ==========================

    @Operation(summary = "Actualizar una venta")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Venta actualizada"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "La venta no existe"
            )
    })

    @PutMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VentaRequestDTO dto) {

        return ResponseEntity.ok(
                service.actualizar(id, dto)
        );
    }

    // ==========================
    // ELIMINAR VENTA
    // ==========================

    @Operation(summary = "Eliminar una venta")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Venta eliminada correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "La venta no existe"
            )
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                "Venta eliminada correctamente"
        );
    }
}
```
