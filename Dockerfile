FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY /target/FileStore-0.0.1-SNAPSHOT.jar /app/file-store.jar
RUN apt-get update && apt-get install -y netcat && apt-get install -y postgresql-client
ENTRYPOINT ["java", "-jar", "file-store.jar"]

