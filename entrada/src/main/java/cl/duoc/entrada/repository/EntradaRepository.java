package cl.duoc.entrada.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.entrada.model.ModeloEntrada;

public interface EntradaRepository
        extends JpaRepository<ModeloEntrada, Long> {
}