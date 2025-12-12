# ----------- Build stage (Maven + JDK) -----------
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Cache dependencies first
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src
RUN mvn -q -DskipTests package

# ----------- Runtime stage (Alpine JRE) -----------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built jar
COPY --from=build /app/target/*.jar /app/app.jar

# Run your main class via the jar
ENTRYPOINT ["java", "-cp", "/app/app.jar", "ma.demo.Main"]