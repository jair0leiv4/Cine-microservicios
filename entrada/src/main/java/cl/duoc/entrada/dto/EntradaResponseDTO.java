package cl.duoc.entrada.dto; // paquete dto

import lombok.AllArgsConstructor; // constructor completo
import lombok.Data; // getters setters
import lombok.NoArgsConstructor; // constructor vacio

@Data // genera getters setters
@NoArgsConstructor // constructor vacio
@AllArgsConstructor // constructor completo
public class EntradaResponseDTO {

    private Long id; // id entrada

    private String tipo; // tipo entrada

    private Integer precio; // precio entrada

    private Long funcionId; // id funcion
}