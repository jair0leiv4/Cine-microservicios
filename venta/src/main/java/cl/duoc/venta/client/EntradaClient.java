package cl.duoc.venta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "entrada")
public interface EntradaClient {

    @GetMapping("/api/entradas/{id}")
    String obtenerPorId(
            @PathVariable Long id
    );
}