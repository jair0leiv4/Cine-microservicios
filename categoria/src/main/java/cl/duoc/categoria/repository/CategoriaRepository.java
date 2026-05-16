package cl.duoc.categoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.categoria.model.ModeloCategoria;

public interface CategoriaRepository
        extends JpaRepository<ModeloCategoria, Long> {
            
}