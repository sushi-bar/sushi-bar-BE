# sushi-bar backend
Apache Kafka Proof of Concept - Integration with Spring Boot
## Technologies
- Confluent Apache Kafka
- Spring Boot
- MongoDB
- Docker
- TestContainers
  
## Build
[![Build](https://github.com/sushi-bar/sushi-bar-BE/actions/workflows/maven.yml/badge.svg)](https://github.com/sushi-bar/sushi-bar-BE/actions/workflows/maven.yml)


## Requirements
Management system of a virtual sushi booth in which each customer can consult the online menu; once registered
can place the order, indicate the table to be served and pay the bill.
Once the bill has been paid, the cook appears with the order to cook and serve to the customer his dinner / lunch.
A room administrator has a dashboard available to manage users, customize and compose the menu with the prices of the courses, supervise the
work of the cook.

## Prerequisites
- java version 21
- maven 3.9.3
- docker 25.0.3  (<https://www.docker.com/>)

## Architecture (draft)
Based on Apache Kafka & spring boot.

- app: main application
- cook: process the order(s)
## Oauth2 Gmail (main app)
Before starting the app, you need to register oauth2 on [google cloud console](https://console.cloud.google.com/).

![img.png](docs/oauth2.png)

This will generate the client Id and the Client Secret. Then replace the default ones in the [application.yml](sushibar-backend-app/src/main/resources/application.yml)

```yml
  security:
    oauth2:
      client:
        registration:
          google :
            clientId: <YOUR-CLIENT-ID>
            clientSecret: <YOUR-CLIENT-SECRET>
```

## Kafka (docker)
From the [scripts](scripts) folder.

### Starting all the docker services
```shell
$ docker compose up -d
```

### Stopping all the docker services
```shell
$ docker compose down
```
### Connecting to the docker container
```shell
$ docker exec -it  sushibar-backend-app-kafka-1 bash (For Windows add winpty before docker)
$ /bin/kafka-topics --list --bootstrap-server localhost:29092
$ /bin/kafka-console-consumer --bootstrap-server localhost:29092 --topic orders --from-beginning
```
#### Delete topic(s)
```shell
/bin/kafka-topics --bootstrap-server localhost:9092 --delete --topic processed-orders
/bin/kafka-topics --bootstrap-server localhost:9092 --delete --topic orders
```

## Kafka-Mongo Connector
Connect to the docker kafka connector instance:

```shell
$ docker exec -it sushibar-backend-kafka_connect_1  bash
```

From the docker container install the kafka connector:
```shell
$ ./confluent-hub install --no-prompt mongodb/kafka-connect-mongodb:latest
```

Configure the kafka connector for the specific topic with postman.

* url: http://localhost:8083/connectors
* method: POST
* body:
```json
{"name": "mongo-sink-connector",
      "config": {
         "connector.class":"com.mongodb.kafka.connect.MongoSinkConnector",
         "connection.uri":"mongodb://root:rootpassword@mongodb_container",
         "database":"order",
         "collection":"sdp_order",
         "topics":"orders",
         "key.converter.schemas.enable":"true",
         "value.converter.schemas.enable": "true",
     "key.converter":"io.confluent.connect.avro.AvroConverter",
     "key.converter.enhanced.avro.schema.support":true,	
         "key.converter.schema.registry.url": "http://schema-registry:8081",
          "value.converter.schema.registry.url": "http://schema-registry:8081",
          "value.converter.enhanced.avro.schema.support":true,
         "value.converter": "io.confluent.connect.avro.AvroConverter"
         }
     }
```

## OpenAPI (backend app)
http://localhost:8080/swagger-ui/index.html

## Troubleshooting
### Schema issue
```shell
org.apache.kafka.common.errors.InvalidConfigurationException: Schema being registered is incompatible with an earlier schema; error code: 409
```
#### workaround
Set the compatibility mode of the topic to NONE.
![compatibility](docs/compatibility-mode.png)

## Compass (MongoDB)
![Compass connection](docs/compass-connection.png)
![Compass data](docs/compass-data.png)

## Credits
[3 Simple Steps to set up Kafka locally using Docker](https://towardsdev.com/3-simple-steps-to-set-up-kafka-locally-using-docker-b07f71f0e2c9)

[Baeldung Kafka](https://www.baeldung.com/ops/kafka-docker-setup)

[Oauth gmail](https://www.youtube.com/watch?v=qcz2jBLNOtc&t=29s)



