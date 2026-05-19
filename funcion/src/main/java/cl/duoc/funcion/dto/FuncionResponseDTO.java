package cl.duoc.funcion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionResponseDTO {

    private Long id;

    private String hora;

    private Long salaId;

    private Long peliculaId;
}