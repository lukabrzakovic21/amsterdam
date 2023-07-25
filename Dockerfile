FROM openjdk:17-jdk-alpine
EXPOSE 9100
ARG JAR_FILE=target/amsterdam-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]