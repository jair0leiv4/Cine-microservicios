package cl.duoc.pago.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pagos")
public class ModeloPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String metodoPago;

    private Integer monto;

    private Long ventaId;

    public ModeloPago() {
    }

    public ModeloPago(Long id,
                      String metodoPago,
                      Integer monto,
                      Long ventaId) {

        this.id = id;
        this.metodoPago = metodoPago;
        this.monto = monto;
        this.ventaId = ventaId;
    }

    public Long getId() {
        return id;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public Integer getMonto() {
        return monto;
    }

    public Long getVentaId() {
        return ventaId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }
}