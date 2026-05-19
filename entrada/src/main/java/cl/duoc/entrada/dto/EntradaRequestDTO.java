package cl.duoc.entrada.dto; // paquete dto

import jakarta.validation.constraints.NotBlank; // valida texto
import jakarta.validation.constraints.NotNull; // valida null
import lombok.AllArgsConstructor; // constructor completo
import lombok.Data; // getters setters
import lombok.NoArgsConstructor; // constructor vacio

@Data // genera getters setters
@NoArgsConstructor // constructor vacio
@AllArgsConstructor // constructor completo
public class EntradaRequestDTO {

    @NotBlank(message = "El tipo es obligatorio") // valida texto vacio
    private String tipo;

    @NotNull(message = "El precio es obligatorio") // valida null
    private Integer precio;

    @NotNull(message = "La funcion es obligatoria") // valida null
    private Long funcionId;
}