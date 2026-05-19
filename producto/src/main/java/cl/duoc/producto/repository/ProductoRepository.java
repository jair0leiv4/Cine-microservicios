package cl.duoc.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.producto.model.ModeloProducto;

public interface ProductoRepository
        extends JpaRepository<ModeloProducto, Long> {
}