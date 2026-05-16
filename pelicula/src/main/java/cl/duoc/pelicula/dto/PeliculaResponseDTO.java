package cl.duoc.pelicula.dto;

public class PeliculaResponseDTO {

    private Long id;
    private String titulo;
    private String genero;
    private Integer duracion;
    private Long categoriaId;

    public PeliculaResponseDTO(Long id,
                               String titulo,
                               String genero,
                               Integer duracion,
                               Long categoriaId) {

        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.categoriaId = categoriaId;
    }

    public Long getId() {
        return id;
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
}