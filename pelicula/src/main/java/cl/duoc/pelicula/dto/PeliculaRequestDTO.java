package cl.duoc.pelicula.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PeliculaRequestDTO {

    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    private String genero;

    @NotNull(message = "La duracion es obligatoria")
    private Integer duracion;

    @NotNull(message = "La categoria es obligatoria")
    private Long categoriaId;

    public PeliculaRequestDTO() {
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}