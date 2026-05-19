package cl.duoc.venta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "entrada-service",
        url = "http://localhost:8086"
)
public interface EntradaClient {

    @GetMapping("/api/entradas/{id}")
    String obtenerEntrada(
            @PathVariable Long id
    );
}