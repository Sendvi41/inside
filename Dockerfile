FROM openjdk:11-jre-slim
EXPOSE 8080
COPY /target/appInside.jar appInside.jar
ENTRYPOINT ["java","-jar","/appInside.jar"]