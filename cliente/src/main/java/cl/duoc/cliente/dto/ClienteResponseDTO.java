package cl.duoc.cliente.dto;

public class ClienteResponseDTO {

    private Long id;
    private String nombre;
    private String correo;
    private String telefono;

    public ClienteResponseDTO(Long id,
                              String nombre,
                              String correo,
                              String telefono) {

        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }
}