# Sistema de Gestión de Cine - Microservicios

## Integrante:
- Jairo Leiva

## Descripción
Proyecto desarrollado con arquitectura de microservicios utilizando Spring Boot, JPA, Hibernate y MySQL.

El sistema permite gestionar:
- Clientes
- Entradas
- Funciones
- Ventas
- Productos
- Películas
- Salas

Además, se implementó comunicación entre microservicios mediante RestTemplate y FeignClient.

---

## Tecnologías utilizadas
- Java
- Spring Boot
- MySQL
- Hibernate
- JPA
- Maven
- Lombok
- Postman
- GitHub

---

## Funcionalidades implementadas
- CRUD de microservicios
- Persistencia con MySQL
- Validaciones
- Manejo de errores
- DTO
- Comunicación entre microservicios
- Logs con SLF4J

---

## Microservicios
- ms-cliente
- ms-entrada
- ms-funcion
- ms-producto
- ms-venta

---

## Dependencias entre microservicios

```text
venta → cliente
venta → entrada
entrada → funcion
```

---

## Ejecución

1. Crear bases de datos en MySQL:
- cliente
- entrada
- funcion
- producto
- venta

2. Ejecutar cada microservicio.

3. Probar endpoints mediante Postman.
