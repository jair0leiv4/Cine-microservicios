package cl.duoc.pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.pago.model.ModeloPago;

public interface PagoRepository
        extends JpaRepository<ModeloPago, Long> {
}