FROM openjdk:8-jdk-alpine
COPY ./mp4ru-service/target/mp4ru-service-*.jar /opt/mp4ru/service/mp4ru-service.jar
ADD ./res/ /opt/mp4ru/service/res/
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,address=2001,server=y,suspend=n","-Djava.security.egd=file:/dev/./urandom","-jar","-Dspring.profiles.active=docker","/opt/mp4ru/service/mp4ru-service.jar"]
