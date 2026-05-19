package cl.duoc.funcion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionRequestDTO {

    @NotBlank(message = "La hora es obligatoria")
    private String hora;

    @NotNull(message = "La sala es obligatoria")
    private Long salaId;

    @NotNull(message = "La pelicula es obligatoria")
    private Long peliculaId;
}