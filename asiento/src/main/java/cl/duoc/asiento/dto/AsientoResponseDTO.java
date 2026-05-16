package cl.duoc.asiento.dto;

public class AsientoResponseDTO {

    private Long id;
    private String numero;
    private Long salaId;

    public AsientoResponseDTO(Long id,
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
}