package cl.duoc.asiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.asiento.model.ModeloAsiento;

public interface AsientoRepository
        extends JpaRepository<ModeloAsiento, Long> {
}