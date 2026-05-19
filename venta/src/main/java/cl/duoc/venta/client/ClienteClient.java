package cl.duoc.venta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "cliente-service",
        url = "http://localhost:8087"
)
public interface ClienteClient {

    @GetMapping("/api/clientes/{id}")
    String obtenerCliente(
            @PathVariable Long id
    );
}