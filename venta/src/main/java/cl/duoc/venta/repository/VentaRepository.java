package cl.duoc.venta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.venta.model.ModeloVenta;

public interface VentaRepository
        extends JpaRepository<ModeloVenta, Long> {
}