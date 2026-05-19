package cl.duoc.pago.service;

import cl.duoc.pago.dto.*;
import cl.duoc.pago.model.ModeloPago;
import cl.duoc.pago.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository repository;
    private final RestTemplate restTemplate;

    private PagoResponseDTO mapToDTO(ModeloPago pago) {

        return new PagoResponseDTO(
                pago.getId(),
                pago.getMetodoPago(),
                pago.getMonto(),
                pago.getVentaId()
        );
    }

    public List<PagoResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public PagoResponseDTO guardar(PagoRequestDTO dto) {

        String ventaURL =
                "http://localhost:8088/api/ventas/"
                        + dto.getVentaId();

        try {

            ResponseEntity<String> response =
                    restTemplate.getForEntity(
                            ventaURL,
                            String.class
                    );

            if(!response.getStatusCode()
                    .is2xxSuccessful()) {

                throw new RuntimeException(
                        "Venta no encontrada"
                );
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error: venta no existe"
            );
        }

        ModeloPago pago =
                new ModeloPago(
                        null,
                        dto.getMetodoPago(),
                        dto.getMonto(),
                        dto.getVentaId()
                );

        return mapToDTO(
                repository.save(pago)
        );
    }
}