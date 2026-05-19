package cl.duoc.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {

    private Long id;

    private String nombre;

    private Integer stock;

    private Integer precio;
}