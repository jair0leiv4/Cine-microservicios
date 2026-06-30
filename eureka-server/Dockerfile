# ─────────────────────────────────────────────────────────
# Dockerfile — eureka-server
# Imagen base: Eclipse Temurin 17 (JDK slim)
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

RUN ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw package -DskipTests -B

# ══════════════════════════════════════════════════════════
#  ETAPA 2: RUNTIME
# ══════════════════════════════════════════════════════════
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-prod}", "-jar", "app.jar"]
