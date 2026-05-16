package cl.duoc.entrada.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "entradas")
public class ModeloEntrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long funcionId;

    private Long asientoId;

    private Integer precio;

    public ModeloEntrada() {
    }

    public ModeloEntrada(Long id,
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

    public void setId(Long id) {
        this.id = id;
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