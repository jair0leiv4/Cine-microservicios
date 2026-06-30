package cl.duoc.pago.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.duoc.pago.dto.PagoRequestDTO;
import cl.duoc.pago.dto.PagoResponseDTO;
import cl.duoc.pago.model.ModeloPago;
import cl.duoc.pago.repository.PagoRepository;
import lombok.RequiredArgsConstructor;

/**
 * Capa de negocio del microservicio Pago.
 *
 * Responsabilidades:
 *  - CRUD completo de pagos.
 *  - Validar que la venta exista antes de registrar un pago
 *    (comunicación REST con microservicio Venta).
 *  - Reglas de negocio: monto debe ser positivo;
 *    método de pago solo puede ser TARJETA, EFECTIVO o TRANSFERENCIA.
 */
@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository repository;
    private final RestTemplate restTemplate;

    // ─── URL base del microservicio Venta ───────────────
    private static final String VENTA_URL =
            "http://localhost:8088/api/ventas/";

    // ─── Métodos de pago válidos ─────────────────────────
    private static final List<String> METODOS_VALIDOS =
            List.of("TARJETA", "EFECTIVO", "TRANSFERENCIA");

    // ════════════════════════════════════════════════════
    //  Conversión Entity → DTO de respuesta
    // ════════════════════════════════════════════════════
    private PagoResponseDTO mapToDTO(ModeloPago p) {
        return new PagoResponseDTO(
                p.getId(), p.getMetodoPago(),
                p.getMonto(), p.getVentaId());
    }

    // ════════════════════════════════════════════════════
    //  Validaciones de negocio reutilizables
    // ════════════════════════════════════════════════════
    private void validarVenta(Long ventaId) {
        try {
            ResponseEntity<String> res =
                    restTemplate.getForEntity(VENTA_URL + ventaId, String.class);
            if (!res.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("La venta con ID " + ventaId + " no existe");
            }
        } catch (Exception e) {
            throw new RuntimeException("La venta con ID " + ventaId + " no existe");
        }
    }

    private void validarMetodoPago(String metodo) {
        if (!METODOS_VALIDOS.contains(metodo.toUpperCase())) {
            throw new RuntimeException(
                    "Método de pago inválido. Use: TARJETA, EFECTIVO o TRANSFERENCIA");
        }
    }

    private void validarMonto(Integer monto) {
        if (monto == null || monto <= 0) {
            throw new RuntimeException("El monto debe ser mayor a cero");
        }
    }

    // ════════════════════════════════════════════════════
    //  LISTAR — GET /api/pagos
    // ════════════════════════════════════════════════════
    public List<PagoResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ════════════════════════════════════════════════════
    //  BUSCAR POR ID — GET /api/pagos/{id}
    // ════════════════════════════════════════════════════
    public Optional<PagoResponseDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    // ════════════════════════════════════════════════════
    //  CREAR — POST /api/pagos
    // ════════════════════════════════════════════════════
    public PagoResponseDTO guardar(PagoRequestDTO dto) {
        validarMonto(dto.getMonto());
        validarMetodoPago(dto.getMetodoPago());
        validarVenta(dto.getVentaId());

        ModeloPago pago = new ModeloPago(
                null,
                dto.getMetodoPago().toUpperCase(),
                dto.getMonto(),
                dto.getVentaId());

        return mapToDTO(repository.save(pago));
    }

    // ════════════════════════════════════════════════════
    //  ACTUALIZAR — PUT /api/pagos/{id}
    // ════════════════════════════════════════════════════
    public PagoResponseDTO actualizar(Long id, PagoRequestDTO dto) {
        ModeloPago pago = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Pago con ID " + id + " no encontrado"));

        validarMonto(dto.getMonto());
        validarMetodoPago(dto.getMetodoPago());
        validarVenta(dto.getVentaId());

        pago.setMetodoPago(dto.getMetodoPago().toUpperCase());
        pago.setMonto(dto.getMonto());
        pago.setVentaId(dto.getVentaId());

        return mapToDTO(repository.save(pago));
    }

    // ════════════════════════════════════════════════════
    //  ELIMINAR — DELETE /api/pagos/{id}
    // ════════════════════════════════════════════════════
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Pago con ID " + id + " no encontrado");
        }
        repository.deleteById(id);
    }
}
