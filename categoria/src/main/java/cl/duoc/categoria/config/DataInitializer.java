package cl.duoc.categoria.config;

import cl.duoc.categoria.model.ModeloCategoria;

import cl.duoc.categoria.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository repository;

    @Override
    public void run(String... args) {

        if (repository.count() > 0) {
            return;
        }

        repository.save(
                new ModeloCategoria(
                        null,
                        "Acción",
                        "Películas de acción"
                )
        );

        repository.save(
                new ModeloCategoria(
                        null,
                        "Terror",
                        "Películas de terror"
                )
        );
    }
}
