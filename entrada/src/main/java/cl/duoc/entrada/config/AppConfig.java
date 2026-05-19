package cl.duoc.entrada.config; // paquete config

import org.springframework.context.annotation.Bean; // bean
import org.springframework.context.annotation.Configuration; // configuracion
import org.springframework.web.client.RestTemplate; // cliente http

@Configuration // clase de configuracion
public class AppConfig {

    @Bean // crea bean manejado por spring
    public RestTemplate restTemplate() {

        return new RestTemplate(); // retorna resttemplate
    }
}