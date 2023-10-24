FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y maven
WORKDIR /app

COPY . /app/
WORKDIR /app
RUN mvn clean install 

EXPOSE 9000

CMD ["java", "-jar", "target/postservice-0.0.1-SNAPSHOT.jar"]