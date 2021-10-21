FROM openjdk:11.0.8-jre
ENV HOST localhost
COPY target/*-0.0.1-jar-with-dependencies.jar /client-1.0.jar
CMD java -jar /client-1.0.jar service.Main $HOST