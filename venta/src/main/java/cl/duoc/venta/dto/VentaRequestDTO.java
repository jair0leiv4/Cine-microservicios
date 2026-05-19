package cl.duoc.venta.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequestDTO {

    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "La entrada es obligatoria")
    private Long entradaId;

    @NotNull(message = "El total es obligatorio")
    private Integer total;
}