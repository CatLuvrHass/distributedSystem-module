# quote-ws

This project implements a sample stock price quotation web service using jms-message oriented middle ware

If you already know what you are doing, run `mvn clean install` in root file, then `mvn exec:java` the files in following order:

 - Broker -> Services -> Client.

Example : `mvn exec:java -pl broker/`


### Compiling the Services

Goto a service folder and build the code by typing:

`mvn package` or `mvn clean install`

### Running the Service

Compile the service and then type:

`mvn exec:java` in current service folder or `mvn exec:java -pl 'Servicefoldername/'` in root folder

Same for the broker and client.

### Dockerising the Service

Compile the service and then type:

`docker build -t service{n}:latest .`

### Compiling the Client

Goto the client folder and build the code by typing:

`mvn package`

### Running the Client

Compile the client and then type:

`mvn exec:java`

### Dockerising the Client

Compile the client and then type:

`docker build -t client:latest .` this works but its not necesary when running the full system.
for some reason it does not work together.

### Compiling the Broker

Goto the broker folder and build the code by typing:

`mvn package`

### Running the Broker

Compile the broker and then type:

`mvn exec:java`

### Dockerising the Broker

Compile the broker and then type:

`docker build -t broker:latest .`

### Running the example with `docker-compose`

To run the full system easy do the following

Go to the root folder "quote-ws" which contains the docker-compose.yml file and type:

`docker-compose build`

`docker-compose up`

Open another terminal and run the client locally with `mvn clean compile java:exec -pl cleint/`

