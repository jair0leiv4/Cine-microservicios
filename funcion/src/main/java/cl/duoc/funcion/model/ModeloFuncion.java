package cl.duoc.funcion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "funciones")
public class ModeloFuncion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fecha;

    private String hora;

    private Long peliculaId;

    private Long salaId;

    public ModeloFuncion() {
    }

    public ModeloFuncion(Long id,
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

    public void setId(Long id) {
        this.id = id;
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