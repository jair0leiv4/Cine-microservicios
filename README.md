# Sistema de Gestión de Cine - Microservicios

## Integrante
- Jairo Leiva

## Descripción
Proyecto desarrollado con arquitectura de microservicios utilizando Spring Boot, JPA, Hibernate y MySQL.
El sistema permite gestionar una sala de cine de forma distribuida, con servicios independientes para cada
dominio funcional del negocio.

---

## Tecnologías utilizadas
- Java 17
- Spring Boot 3.3.5
- Spring Cloud Gateway (MVC)
- Spring Cloud Netflix Eureka
- OpenFeign
- MySQL
- Hibernate / JPA
- Maven
- Lombok
- Swagger / OpenAPI (springdoc-openapi 2.6.0)
- JUnit 5 + Mockito
- DataFaker
- GitHub

---

## Microservicios implementados

| Microservicio | Puerto | Base de datos |
|---------------|--------|---------------|
| eureka-server | 8761   | -             |
| api-gateway   | 8090   | -             |
| pelicula      | 8081   | pelicula      |
| funcion       | 8082   | funcion       |
| sala          | 8083   | sala          |
| categoria     | 8084   | categoria     |
| producto      | 8085   | producto      |
| entrada       | 8086   | entrada       |
| cliente       | 8087   | cliente       |
| venta         | 8088   | venta         |
| pago          | 8089   | pago          |
| asiento       | 8091   | asiento       |

---

## Dependencias entre microservicios

```
venta     → cliente  (FeignClient)
venta     → entrada  (FeignClient)
pago      → venta    (RestTemplate)
pelicula  → categoria (RestTemplate)
asiento   → sala     (RestTemplate)
```

---

## Rutas principales del Gateway (puerto 8090)

| Ruta                      | Microservicio |
|---------------------------|---------------|
| /api/categorias/**        | categoria     |
| /api/peliculas/**         | pelicula      |
| /api/salas/**             | sala          |
| /api/asientos/**          | asiento       |
| /api/funciones/**         | funcion       |
| /api/entradas/**          | entrada       |
| /api/productos/**         | producto      |
| /api/clientes/**          | cliente       |
| /api/ventas/**            | venta         |
| /api/pagos/**             | pago          |

---

## Documentación Swagger (local)

Cada microservicio expone Swagger en su puerto:

- Pelicula:   http://localhost:8081/swagger-ui/index.html
- Funcion:    http://localhost:8082/swagger-ui/index.html
- Sala:       http://localhost:8083/swagger-ui/index.html
- Categoria:  http://localhost:8084/swagger-ui/index.html
- Producto:   http://localhost:8085/swagger-ui/index.html
- Entrada:    http://localhost:8086/swagger-ui/index.html
- Cliente:    http://localhost:8087/swagger-ui/index.html
- Venta:      http://localhost:8088/swagger-ui/index.html
- Pago:       http://localhost:8089/swagger-ui/index.html
- Asiento:    http://localhost:8091/swagger-ui/index.html

---

## Instrucciones de ejecución local

### Requisitos previos
- Java 17
- Maven
- MySQL corriendo en localhost:3306

### 1. Crear bases de datos en MySQL

```sql
CREATE DATABASE categoria;
CREATE DATABASE pelicula;
CREATE DATABASE sala;
CREATE DATABASE asiento;
CREATE DATABASE funcion;
CREATE DATABASE entrada;
CREATE DATABASE producto;
CREATE DATABASE cliente;
CREATE DATABASE venta;
CREATE DATABASE pago;
```

### 2. Orden de ejecución de microservicios

Ejecutar en este orden (desde cada carpeta con `./mvnw spring-boot:run`):

1. `eureka-server`   → esperar a que inicie en http://localhost:8761
2. `categoria`
3. `sala`
4. `pelicula`
5. `funcion`
6. `asiento`
7. `entrada`
8. `producto`
9. `cliente`
10. `venta`
11. `pago`
12. `api-gateway`    → iniciar al final

### 3. Probar endpoints

Usar Postman apuntando al Gateway: http://localhost:8090

Ejemplo:
- GET  http://localhost:8090/api/clientes
- POST http://localhost:8090/api/clientes

---

## Instrucciones de ejecución con Docker

```bash
# Construir imagen (desde la raíz del proyecto)
docker build -t cine-ms .

# Ejecutar con Docker
docker run -p 8090:8090 cine-ms
```

---

## Pruebas unitarias y cobertura (JaCoCo)

Cada microservicio contiene pruebas unitarias en `src/test/java` con JUnit 5 y Mockito,
y tiene configurado el plugin **JaCoCo** para medir cobertura, con un mínimo exigido del **80%** de líneas cubiertas.

```bash
# Ejecutar pruebas + generar reporte de cobertura de un microservicio
cd venta
./mvnw clean verify

# El reporte HTML queda en:
# target/site/jacoco/index.html

# Si la cobertura es menor al 80%, el build falla en la fase "verify"
# (regla configurada en el pom.xml de cada microservicio)
```

Para revisar la cobertura de todos los microservicios de una vez:

```bash
for d in pelicula funcion sala categoria producto entrada cliente venta pago asiento; do
  echo "=== $d ==="
  (cd $d && ./mvnw clean verify)
done
```

---

## Configuración por perfiles (YAML)

Cada microservicio usa `application.yml` + perfiles (`dev` / `prod`):

- **`application.yml`**: configuración base (nombre de la app, Swagger, perfil activo por defecto).
- **`application-dev.yml`**: configuración para ejecución local (MySQL en `localhost:3306`, Eureka en `localhost:8761`).
- **`application-prod.yml`**: configuración para despliegue remoto (Railway/Render). Todos los valores sensibles
  (URL de base de datos, usuario, password, URL de Eureka) se leen desde **variables de entorno**.

El perfil activo se controla con la variable de entorno `SPRING_PROFILES_ACTIVE` (por defecto `dev`).
Los `Dockerfile` activan automáticamente el perfil `prod` al correr en contenedor.

Variables de entorno requeridas en producción para cada microservicio con base de datos:

| Variable | Descripción |
|----------|-------------|
| `DB_URL` | JDBC URL, ej: `jdbc:mysql://<host>:<puerto>/<db>` |
| `DB_USERNAME` | Usuario de la base de datos |
| `DB_PASSWORD` | Password de la base de datos |
| `EUREKA_URL` | URL pública del Eureka Server desplegado, ej: `https://eureka-server.up.railway.app/eureka` |
| `PORT` | Puerto asignado automáticamente por la plataforma (Railway/Render lo inyectan solos) |

---

## Despliegue remoto (Railway)

Orden de despliegue recomendado (cada microservicio es un servicio independiente en el mismo proyecto de Railway):

1. **MySQL**: crear un plugin de MySQL en Railway (o una base por servicio si prefieres aislarlas). Railway entrega
   automáticamente las variables `MYSQLHOST`, `MYSQLPORT`, `MYSQLDATABASE`, `MYSQLUSER`, `MYSQLPASSWORD`.
2. **eureka-server**: nuevo servicio → "Deploy from GitHub repo" → root directory `eureka-server` → Railway detecta
   el `Dockerfile` y construye solo. Anota la URL pública (ej. `https://eureka-server-production.up.railway.app`).
3. Para cada uno de los 10 microservicios (categoria, pelicula, sala, asiento, funcion, entrada, producto, cliente,
   venta, pago):
   - Nuevo servicio → root directory = nombre del microservicio (ej. `pelicula`).
   - Variables de entorno:
     - `SPRING_PROFILES_ACTIVE=prod`
     - `DB_URL=jdbc:mysql://${{MYSQLHOST}}:${{MYSQLPORT}}/${{MYSQLDATABASE}}` (o el nombre de BD específico)
     - `DB_USERNAME=${{MYSQLUSER}}`
     - `DB_PASSWORD=${{MYSQLPASSWORD}}`
     - `EUREKA_URL=https://eureka-server-production.up.railway.app/eureka`
4. **api-gateway**: mismo procedimiento, root directory `api-gateway`, variable `EUREKA_URL` apuntando al Eureka
   público. Esta es la URL que se comparte para probar todo el sistema (ej. `https://api-gateway-production.up.railway.app`).
5. Verificar que todos los servicios figuren como "UP" en el dashboard de Eureka (`https://eureka-server.../`) antes
   de probar el Gateway.

> Alternativa: el mismo procedimiento aplica en **Render** (Web Service → Docker → root directory del microservicio +
> variables de entorno equivalentes), cumpliendo así el requisito de desplegar en al menos dos entornos/herramientas.
