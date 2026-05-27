# ============================================
# Stage 1: Build with Maven
# ============================================
FROM maven:3.9.7-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Cache dependencies (failsafe: ignore errors if no pom yet)
COPY pom.xml .
RUN mvn dependency:go-offline -B -q || true

# Compile & package
COPY src ./src
RUN mvn clean package -DskipTests -B -q

# ============================================
# Stage 2: Production runtime
# ============================================
FROM eclipse-temurin:17-jre-alpine AS runtime

# Install curl for health check
RUN apk add --no-cache curl tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

WORKDIR /app

# Non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Create uploads directory
RUN mkdir -p /app/uploads/avatars && chown -R appuser:appgroup /app/uploads

# Copy the built jar
COPY --from=build /app/target/*.jar app.jar

# Security
USER appuser

EXPOSE 8080

# JVM options optimized for containers
# -XX:+UseContainerSupport: auto-detects container memory limits
# -XX:MaxRAMPercentage=75.0: max heap = 75% of container memory
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:+ExitOnOutOfMemoryError", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "app.jar"]

HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
    CMD curl -sf http://localhost:8080/api/jobs/hot || exit 1
