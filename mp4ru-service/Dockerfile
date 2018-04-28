FROM openjdk:8-jdk-alpine
COPY target/mp4ru-service-*.jar /opt/mp4ru/service/mp4ru-service.jar
ADD res/russian.mco /opt/mp4ru/service/res/russian.mco
ADD res/russian-utf8.par /opt/mp4ru/service/res/russian-utf8.par
ADD res/bin/tree-tagger.exe /opt/mp4ru/service/res/bin/tree-tagger.exe
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,address=2001,server=y,suspend=n","-Djava.security.egd=file:/dev/./urandom","-jar","-Dspring.profiles.active=docker","/opt/mp4ru/service/mp4ru-service.jar"]
