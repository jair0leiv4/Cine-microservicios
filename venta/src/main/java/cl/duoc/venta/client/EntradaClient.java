package cl.duoc.venta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "entrada",
        url = "http://localhost:8086/api/entradas"
)
public interface EntradaClient {

    @GetMapping("/{id}")
    String obtenerPorId(
            @PathVariable Long id
    );
}