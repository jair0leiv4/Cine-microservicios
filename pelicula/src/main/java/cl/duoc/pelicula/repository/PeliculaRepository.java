package cl.duoc.pelicula.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.pelicula.model.ModeloPelicula;

public interface PeliculaRepository
        extends JpaRepository<ModeloPelicula, Long> {
}