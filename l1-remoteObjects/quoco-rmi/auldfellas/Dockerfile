FROM openjdk:8-jre-alpine
ENV SERVER_HOST=localhost
COPY target/auldfellas-*-jar-with-dependencies.jar /auldfellas.jar
CMD /usr/bin/java -jar /auldfellas.jar $SERVER_HOST