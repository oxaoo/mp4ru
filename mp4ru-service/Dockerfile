FROM openjdk:8-jdk-alpine
COPY target/mp4ru-service-0.2.0.jar /opt/mp4ru/service/mp4ru-service.jar
ADD res/russian.mco /opt/mp4ru/service/res/russian.mco
ADD res/russian-utf8.par /opt/mp4ru/service/res/russian-utf8.par
ADD res/bin/tree-tagger.exe /opt/mp4ru/service/res/bin/tree-tagger.exe
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/mp4ru/service/mp4ru-service.jar"]
