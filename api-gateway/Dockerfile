# ─────────────────────────────────────────────────────────
# Dockerfile — API Gateway
# Build en 2 etapas: compila el JAR y luego lo ejecuta
# ─────────────────────────────────────────────────────────

# ══════════════════════════════════════════════════════════
#  ETAPA 1: BUILD
# ══════════════════════════════════════════════════════════
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Descarga dependencias sin compilar (aprovecha cache Docker)
RUN ./mvnw dependency:go-offline -B

COPY src ./src

# Compila y empaqueta (omite tests — el gateway no tiene BD)
RUN ./mvnw package -DskipTests -B

# ══════════════════════════════════════════════════════════
#  ETAPA 2: RUNTIME
# ══════════════════════════════════════════════════════════
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Puerto del gateway
EXPOSE 8090

ENTRYPOINT ["java", "-jar", "app.jar"]
