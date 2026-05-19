package cl.duoc.venta.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.venta.client.ClienteClient;
import cl.duoc.venta.client.EntradaClient;
import cl.duoc.venta.dto.VentaRequestDTO;
import cl.duoc.venta.dto.VentaResponseDTO;
import cl.duoc.venta.model.ModeloVenta;
import cl.duoc.venta.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j

public class VentaService {

    private final VentaRepository repository;

    private final ClienteClient clienteClient;

    private final EntradaClient entradaClient;

    private VentaResponseDTO mapToDTO(
            ModeloVenta venta) {

        return new VentaResponseDTO(
                venta.getId(),
                venta.getClienteId(),
                venta.getEntradaId(),
                venta.getTotal()
        );
    }

    public List<VentaResponseDTO> listar() {

        log.info("Listando ventas");

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public VentaResponseDTO guardar(
            VentaRequestDTO dto) {

        try {

            clienteClient.obtenerCliente(
                    dto.getClienteId()
            );

            entradaClient.obtenerEntrada(
                    dto.getEntradaId()
            );

        } catch (Exception e) {

            log.error(
                    "Error validando microservicios"
            );

            throw new RuntimeException(
                    "Cliente o entrada no existen"
            );
        }

        ModeloVenta venta =
                new ModeloVenta(
                        null,
                        dto.getClienteId(),
                        dto.getEntradaId(),
                        dto.getTotal()
                );

        log.info("Guardando venta");

        return mapToDTO(
                repository.save(venta)
        );
    }
}