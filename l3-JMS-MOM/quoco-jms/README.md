# quote-ws

This project implements a sample stock price quotation web service using jms-message oriented middle ware

if you already know what you are doing, run `mvn clean install` in root file, then `mvn exec:java` the files in following order:

 - Broker -> Services -> Client.

# Compiling the Services

Goto a service folder and build the code by typing:

`mvn package` or `mvn clean install`

# Running the Service

Compile the service and then type:

`mvn exec:java` in current service folder or `mvn exec:java -pl 'Servicefoldername/'` in root folder

Same for the broker and client.

# Dockerising the Service

Compile the service and then type:

`docker build -t service{n}:latest .`

# Compiling the Client

Goto the client folder and build the code by typing:

`mvn package`

# Running the Client

Compile the client and then type:

`mvn exec:java`

# Dockerising the Client

Compile the client and then type:

`docker build -t client:latest .`

# Compiling the Broker

Goto the broker folder and build the code by typing:

`mvn package`

# Running the Broker

Compile the broker and then type:

`mvn exec:java`

# Dockerising the Broker

Compile the broker and then type:

`docker build -t broker:latest .`

# Running the example with `docker-compose`

Go to the root folder "quote-ws" which contains the docker-compose.yml file and type:

`docker-compose build`

`docker-compose up`

this currently doesn't fully work as they all run but it hangs. if you run the files indvidually it should work.
