package cl.duoc.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Punto de entrada del API Gateway.
 *
 * @SpringBootApplication   — configura automáticamente Spring Boot.
 * @EnableDiscoveryClient   — habilita la comunicación con Eureka para
 *                            que las rutas "lb://SERVICIO" funcionen
 *                            correctamente (load balancing via Eureka).
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
