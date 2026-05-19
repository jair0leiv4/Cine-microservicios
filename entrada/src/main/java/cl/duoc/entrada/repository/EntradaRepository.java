package cl.duoc.entrada.repository; // paquete repository

import org.springframework.data.jpa.repository.JpaRepository; // importar modelo

import cl.duoc.entrada.model.ModeloEntrada; // JPA

public interface EntradaRepository
        extends JpaRepository<ModeloEntrada, Long> {
        // CRUD automatico
}