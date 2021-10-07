FROM openjdk:8-jre-alpine
ENV SERVER_HOST=localhost
COPY target/dodgydrivers-*-jar-with-dependencies.jar /dodgydrivers.jar
CMD /usr/bin/java -jar /dodgydrivers.jar $SERVER_HOST