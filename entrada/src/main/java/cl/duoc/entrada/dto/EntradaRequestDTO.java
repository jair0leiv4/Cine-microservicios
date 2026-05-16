package cl.duoc.entrada.dto;

import jakarta.validation.constraints.NotNull;

public class EntradaRequestDTO {

    @NotNull(message = "La funcion es obligatoria")
    private Long funcionId;

    @NotNull(message = "El asiento es obligatorio")
    private Long asientoId;

    @NotNull(message = "El precio es obligatorio")
    private Integer precio;

    public EntradaRequestDTO() {
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

    public void setFuncionId(Long funcionId) {
        this.funcionId = funcionId;
    }

    public void setAsientoId(Long asientoId) {
        this.asientoId = asientoId;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
}