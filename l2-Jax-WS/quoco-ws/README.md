# quote-ws

This project implements a sample stock price quotation web service using Jax-WS

# Compiling the Service

Goto a service folder and build the code by typing:

`mvn package`

# Running the Service

Compile the service and then type:

`mvn exec:java`

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
