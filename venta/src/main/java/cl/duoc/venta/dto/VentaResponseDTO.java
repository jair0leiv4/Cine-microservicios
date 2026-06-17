package cl.duoc.venta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponseDTO
        extends RepresentationModel<VentaResponseDTO> {

    private Long id;

    private Long clienteId;

    private Long entradaId;

    private Integer total;
}