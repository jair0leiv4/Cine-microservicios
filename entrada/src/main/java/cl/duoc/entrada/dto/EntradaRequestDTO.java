package cl.duoc.entrada.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntradaRequestDTO {

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotNull(message = "El precio es obligatorio")
    private Integer precio;

    @NotNull(message = "La funcion es obligatoria")
    private Long funcionId;
}