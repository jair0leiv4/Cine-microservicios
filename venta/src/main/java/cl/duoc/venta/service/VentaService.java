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

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository repository;

    private final ClienteClient clienteClient;

    private final EntradaClient entradaClient;

    // CONVERTIR ENTITY A DTO
    private VentaResponseDTO mapToDTO(
            ModeloVenta venta) {

        return new VentaResponseDTO(
                venta.getId(),
                venta.getClienteId(),
                venta.getEntradaId(),
                venta.getTotal()
        );
    }

    // LISTAR TODAS LAS VENTAS
    public List<VentaResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // BUSCAR VENTA POR ID
    public VentaResponseDTO buscarPorId(Long id) {

        ModeloVenta venta = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "La venta no existe"
                        ));

        return mapToDTO(venta);
    }

    // CREAR VENTA
    public VentaResponseDTO guardar(
            VentaRequestDTO dto) {

        // VALIDAR CLIENTE
        try {

            clienteClient.obtenerPorId(
                    dto.getClienteId()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "El cliente no existe"
            );
        }

        // VALIDAR ENTRADA
        try {

            entradaClient.obtenerPorId(
                    dto.getEntradaId()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "La entrada no existe"
            );
        }

        ModeloVenta venta =
                new ModeloVenta(
                        null,
                        dto.getClienteId(),
                        dto.getEntradaId(),
                        dto.getTotal()
                );

        return mapToDTO(
                repository.save(venta)
        );
    }

    // ACTUALIZAR VENTA
    public VentaResponseDTO actualizar(
            Long id,
            VentaRequestDTO dto) {

        ModeloVenta venta = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "La venta no existe"
                        ));

        // VALIDAR CLIENTE
        try {

            clienteClient.obtenerPorId(
                    dto.getClienteId()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "El cliente no existe"
            );
        }

        // VALIDAR ENTRADA
        try {

            entradaClient.obtenerPorId(
                    dto.getEntradaId()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "La entrada no existe"
            );
        }

        venta.setClienteId(
                dto.getClienteId()
        );

        venta.setEntradaId(
                dto.getEntradaId()
        );

        venta.setTotal(
                dto.getTotal()
        );

        return mapToDTO(
                repository.save(venta)
        );
    }

    // ELIMINAR VENTA
    public void eliminar(Long id) {

        if (!repository.existsById(id)) {

            throw new RuntimeException(
                    "La venta no existe"
            );
        }

        repository.deleteById(id);
    }
}