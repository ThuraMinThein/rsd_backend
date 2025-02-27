FROM amazoncorretto:23
WORKDIR /app
VOLUME /tmp
COPY target/yaycha-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]