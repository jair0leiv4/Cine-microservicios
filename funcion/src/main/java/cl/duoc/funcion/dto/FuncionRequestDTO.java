package cl.duoc.funcion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FuncionRequestDTO {

    @NotBlank(message = "La fecha es obligatoria")
    private String fecha;

    @NotBlank(message = "La hora es obligatoria")
    private String hora;

    @NotNull(message = "La pelicula es obligatoria")
    private Long peliculaId;

    @NotNull(message = "La sala es obligatoria")
    private Long salaId;

    public FuncionRequestDTO() {
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public Long getPeliculaId() {
        return peliculaId;
    }

    public Long getSalaId() {
        return salaId;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setPeliculaId(Long peliculaId) {
        this.peliculaId = peliculaId;
    }

    public void setSalaId(Long salaId) {
        this.salaId = salaId;
    }
}