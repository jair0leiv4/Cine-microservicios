package cl.duoc.entrada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntradaResponseDTO {

    private Long id;

    private String tipo;

    private Integer precio;

    private Long funcionId;
}