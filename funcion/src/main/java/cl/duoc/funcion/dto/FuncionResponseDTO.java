package cl.duoc.funcion.dto;

public class FuncionResponseDTO {

    private Long id;
    private String fecha;
    private String hora;
    private Long peliculaId;
    private Long salaId;

    public FuncionResponseDTO(Long id,
                              String fecha,
                              String hora,
                              Long peliculaId,
                              Long salaId) {

        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.peliculaId = peliculaId;
        this.salaId = salaId;
    }

    public Long getId() {
        return id;
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
}