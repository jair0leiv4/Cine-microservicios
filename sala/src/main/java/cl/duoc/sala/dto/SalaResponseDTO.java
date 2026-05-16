package cl.duoc.sala.dto;

public class SalaResponseDTO {

    private Long id;
    private String nombre;
    private Integer capacidad;

    public SalaResponseDTO(Long id,
                           String nombre,
                           Integer capacidad) {

        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }
}