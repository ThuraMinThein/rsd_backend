FROM amazoncorretto:23
VOLUME /tmp
COPY target/yaycha-0.0.1-SNAPSHOT.jar yaycha.jar
ENTRYPOINT ["java","-jar","/yaycha.jar"]