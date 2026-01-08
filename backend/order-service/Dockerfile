# Multi-stage build for smaller image size

# Stage 1: Build the application
FROM maven:3.9-amazoncorretto-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM amazoncorretto:21-alpine
WORKDIR /app

# Copy the jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8072

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]