FROM openjdk:11.0.8-jre
ENV HOST localhost
COPY target/*-0.0.1-jar-with-dependencies.jar /girlpower.jar
CMD sleep 7 && java -jar /girlpower.jar service.Receiver $HOST