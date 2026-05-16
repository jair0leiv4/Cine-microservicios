package cl.duoc.asiento.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "asientos")
public class ModeloAsiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    private Long salaId;

    public ModeloAsiento() {
    }

    public ModeloAsiento(Long id,
                         String numero,
                         Long salaId) {

        this.id = id;
        this.numero = numero;
        this.salaId = salaId;
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public Long getSalaId() {
        return salaId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setSalaId(Long salaId) {
        this.salaId = salaId;
    }
}