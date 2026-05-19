package cl.duoc.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "Correo invalido")
    private String correo;

    @NotBlank(message = "El telefono es obligatorio")
    private String telefono;
}