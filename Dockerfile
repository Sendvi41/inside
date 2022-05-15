FROM openjdk:11-jre-slim
EXPOSE 8081
COPY /target/appInside.jar appInside.jar
ENTRYPOINT ["java","-jar","/appInside.jar"]