package cl.duoc.pago.dto;

public class PagoResponseDTO {

    private Long id;
    private String metodoPago;
    private Integer monto;
    private Long ventaId;

    public PagoResponseDTO(Long id,
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
}