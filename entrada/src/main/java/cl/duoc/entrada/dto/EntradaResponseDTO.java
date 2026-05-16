package cl.duoc.entrada.dto;

public class EntradaResponseDTO {

    private Long id;
    private Long funcionId;
    private Long asientoId;
    private Integer precio;

    public EntradaResponseDTO(Long id,
                               Long funcionId,
                               Long asientoId,
                               Integer precio) {

        this.id = id;
        this.funcionId = funcionId;
        this.asientoId = asientoId;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public Long getFuncionId() {
        return funcionId;
    }

    public Long getAsientoId() {
        return asientoId;
    }

    public Integer getPrecio() {
        return precio;
    }
}