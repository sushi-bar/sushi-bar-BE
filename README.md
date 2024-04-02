# sushi-bar-BE 
Back End of the sushi bar application.

[![sushi-bar](https://circleci.com/gh/sushi-bar/sushi-bar-BE.svg?style=svg)](https://app.circleci.com/pipelines/github/sushi-bar/sushi-bar-BE)


# Requirement
Management system of a virtual sushi booth in which each customer can consult the online menu; once registered
can place the order, indicate the table to be served and pay the bill.
Once the bill has been paid, the cook appears with the order to cook and serve to the customer his dinner / lunch.
A room administrator has a dashboard available to manage users, customize and compose the menu with the prices of the courses, supervise the
work of the cook.

## Prerequisites

- jdk 11.0.4 or higher
- maven 3.6.1 or higher
- docker 1.6.0 or higher (<https://www.docker.com/>)

## Architecture (draft)
Based on Apache Kafka & spring boot.

![Architecture Draft](docs/draft-arch.jpeg)

## Kafka (docker)

Starting Kafka
```shell
$ docker compose up -d
```

Stopping Kafka
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

### OpenAPI (backend app)
http://localhost:8080/swagger-ui/index.html

