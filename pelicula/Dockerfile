# ─────────────────────────────────────────────────────────
# Dockerfile — Microservicio: pelicula
# Imagen base: Eclipse Temurin 17 (JDK slim)
# Build en 2 etapas: compila el JAR y luego lo ejecuta
# ─────────────────────────────────────────────────────────

# ══════════════════════════════════════════════════════════
#  ETAPA 1: BUILD
#  Usa Maven para compilar y empaquetar el proyecto.
# ══════════════════════════════════════════════════════════
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Copia el wrapper de Maven y el pom.xml primero
# (aprovecha el cache de Docker — solo re-descarga deps si cambia pom.xml)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Descarga dependencias sin compilar el código fuente
RUN ./mvnw dependency:go-offline -B

# Copia el código fuente y compila (omite tests para no requerir MySQL)
COPY src ./src
RUN ./mvnw package -DskipTests -B

# ══════════════════════════════════════════════════════════
#  ETAPA 2: RUNTIME
#  Solo copia el JAR generado en una imagen JRE liviana.
# ══════════════════════════════════════════════════════════
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia el JAR compilado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto del microservicio
# (cambia en docker-compose según el puerto real de cada servicio)
EXPOSE 8080

# Comando de inicio con perfil de producción
ENTRYPOINT ["java", "-jar", "app.jar"]
