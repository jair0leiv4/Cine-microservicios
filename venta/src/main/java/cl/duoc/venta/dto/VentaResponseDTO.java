package cl.duoc.venta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponseDTO {

    private Long id;// ID de la venta, se genera automáticamente al guardar la venta en la base de datos

    private Long clienteId;

    private Long entradaId;

    private Integer total;
}