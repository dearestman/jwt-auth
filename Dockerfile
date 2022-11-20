FROM openjdk:11
MAINTAINER netunix
WORKDIR /app
COPY target/inside-messages-0.0.1-SNAPSHOT.jar .
EXPOSE 8001
CMD ["java","-jar","-Dserver.port=8001", "/app/inside-messages-0.0.1-SNAPSHOT.jar"]