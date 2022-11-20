FROM openjdk:11
MAINTAINER netunix
WORKDIR /app
COPY target/inside-messages-0.0.1-SNAPSHOT.jar .
EXPOSE 8001
CMD ["java","-jar","/app/inside-messages-0.0.1-SNAPSHOT.jar"]