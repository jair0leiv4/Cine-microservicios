package cl.duoc.pago.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PagoRequestDTO {

    @NotBlank(message = "Metodo obligatorio")
    private String metodoPago;

    @NotNull(message = "Monto obligatorio")
    private Integer monto;

    @NotNull(message = "Venta obligatoria")
    private Long ventaId;

    public PagoRequestDTO() {
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