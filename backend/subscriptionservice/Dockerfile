# Use Maven base image to build the project
FROM maven as build

WORKDIR /app

# Copy pom.xml and download dependencies to take advantage of Docker caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source files and build the project
COPY src ./src
RUN mvn clean package -DskipTests

# Use a smaller base image for the actual application
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/userservice-*.jar userservice.jar

# Run the application
CMD ["java", "-jar", "userservice.jar"]