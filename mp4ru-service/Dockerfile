FROM openjdk:8-jdk-alpine
WORKDIR /opt/mp4ru/service
COPY ./mp4ru-service/target/mp4ru-service-*.jar mp4ru-service.jar
COPY ./res/ res/
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,address=2001,server=y,suspend=n","-Djava.security.egd=file:/dev/./urandom","-jar","mp4ru-service.jar"]
