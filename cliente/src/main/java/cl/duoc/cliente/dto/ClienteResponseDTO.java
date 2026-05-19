package cl.duoc.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {

    private Long id;

    private String nombre;

    private String correo;

    private String telefono;
}