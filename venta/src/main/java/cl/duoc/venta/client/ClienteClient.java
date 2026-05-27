package cl.duoc.venta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "cliente",
        url = "http://localhost:8087/api/clientes"
)
public interface ClienteClient {

    @GetMapping("/{id}")
    String obtenerPorId(
            @PathVariable Long id
    );
}