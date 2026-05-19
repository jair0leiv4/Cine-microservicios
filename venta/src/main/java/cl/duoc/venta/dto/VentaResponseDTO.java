package cl.duoc.venta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponseDTO {

    private Long id;

    private Long clienteId;

    private Long entradaId;

    private Integer total;
}