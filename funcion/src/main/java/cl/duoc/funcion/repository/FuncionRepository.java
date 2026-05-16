package cl.duoc.funcion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.funcion.model.ModeloFuncion;

public interface FuncionRepository
        extends JpaRepository<ModeloFuncion, Long> {
}