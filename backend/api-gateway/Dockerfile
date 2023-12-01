# ---- Build Stage ----
FROM openjdk:17-jdk-slim AS build

# Set the working directory
WORKDIR /app

# Install maven
RUN apt-get update && apt-get install -y maven

# Copy the pom.xml file to download dependencies
COPY pom.xml .

# Download the dependencies first (this will be cached unless the pom.xml file changes)
RUN mvn dependency:go-offline

# Copy the source code
COPY src/ src/

# Build the application
RUN mvn clean package -DskipTests

# ---- Run Stage ----
FROM openjdk:17.0.1-jdk-slim

# Make port 8080 available to the world outside this container
EXPOSE 3000

# Set the working directory
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/target/*.jar gateway-service.jar

# Command to run the application
CMD ["java", "-jar", "gateway-service.jar"]
