FROM openjdk:11-jre-slim

COPY ./build/libs/app.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]