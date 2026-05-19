package cl.duoc.producto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El stock es obligatorio")
    private Integer stock;

    @NotNull(message = "El precio es obligatorio")
    private Integer precio;
}