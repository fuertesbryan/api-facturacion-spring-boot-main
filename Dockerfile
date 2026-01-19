# Etapa 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar solo pom.xml primero para mejor cache
COPY pom.xml .

# Descargar dependencias (caché independiente)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar y empaquetar
RUN mvn clean package -DskipTests -B

# Etapa 2: Runtime  
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app

# Para Alpine (más pequeño), instala ca-certificates
RUN apk update && apk add --no-cache ca-certificates && update-ca-certificates

# Crear usuario no-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Copiar JAR desde etapa build
COPY --from=build /app/target/*.jar app.jar

# Exponer puerto
EXPOSE 8080

# Optimizaciones JVM para contenedores
ENV JAVA_OPTS="-Xmx512m -Xms256m \
               -XX:+UseContainerSupport \
               -XX:MaxRAMPercentage=75.0 \
               -Djava.security.egd=file:/dev/./urandom \
               -Dspring.profiles.active=prod"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/facturas || exit 1

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]