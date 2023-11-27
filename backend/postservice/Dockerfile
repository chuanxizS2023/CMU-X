FROM openjdk:17-jdk-slim

# Install necessary utilities
RUN apt-get update && apt-get install -y maven curl
RUN curl -sL https://deb.nodesource.com/setup_14.x | bash -
RUN apt-get install -y nodejs
RUN npm install -g nodemon

# Set the working directory in the container
WORKDIR /app

# Copy local files to the container
COPY . /app/

# Build the application
RUN mvn clean install -DskipTests

# Expose the port the app runs on
EXPOSE 9000

# Command to run the application
CMD ["nodemon", "--watch", "src", "--exec", "mvn", "spring-boot:run"]
