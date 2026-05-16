package cl.duoc.asiento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AsientoRequestDTO {

    @NotBlank(message = "El numero es obligatorio")
    private String numero;

    @NotNull(message = "La sala es obligatoria")
    private Long salaId;

    public AsientoRequestDTO() {
    }

    public String getNumero() {
        return numero;
    }

    public Long getSalaId() {
        return salaId;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setSalaId(Long salaId) {
        this.salaId = salaId;
    }
}