package cl.duoc.sala.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.sala.model.ModeloSala;

public interface SalaRepository
        extends JpaRepository<ModeloSala, Long> {
}