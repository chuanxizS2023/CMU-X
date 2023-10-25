FROM openjdk:17-jdk-slim

# Install necessary utilities
RUN apt-get update && apt-get install -y maven

# Set the working directory in the container
WORKDIR /app

# Copy local files to the container
COPY . /app/

# Build the application
RUN mvn clean install -DskipTests

# Expose the port the app runs on
EXPOSE 9000

# Command to run the application
CMD ["java", "-jar", "target/postservice-0.0.1-SNAPSHOT.jar"]
