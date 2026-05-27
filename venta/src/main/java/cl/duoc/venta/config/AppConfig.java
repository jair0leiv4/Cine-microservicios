package cl.duoc.venta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;// Importa la clase RestTemplate, que es una clase de Spring para realizar solicitudes HTTP a otros microservicios

@Configuration // Anotación para indicar que esta clase es una clase de configuración de Spring, donde se pueden definir beans y otras configuraciones
public class AppConfig { // Clase de configuración para la aplicación, donde se pueden definir beans y otras configuraciones necesarias para el funcionamiento de la aplicación

    @Bean // Anotación para indicar que este método devuelve un bean que debe ser gestionado por el contenedor de Spring, en este caso, un RestTemplate para realizar solicitudes HTTP a otros microservicios
    public RestTemplate restTemplate() { // Método que devuelve un RestTemplate, que es una clase de Spring para realizar solicitudes HTTP a otros microservicios, se puede utilizar para comunicarse con los microservicios de clientes y entradas si no se utiliza Feign
        return new RestTemplate();// Devuelve una nueva instancia de RestTemplate, que se puede inyectar en otras partes de la aplicación para realizar solicitudes HTTP a otros microservicios
    }
}