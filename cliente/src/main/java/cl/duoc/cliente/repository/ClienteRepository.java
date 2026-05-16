package cl.duoc.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.cliente.model.ModeloCliente;

public interface ClienteRepository
        extends JpaRepository<ModeloCliente, Long> {
}