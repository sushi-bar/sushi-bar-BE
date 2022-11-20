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
Connecting to the docker container
```shell

$ docker exec -it sushi-bar-be-kafka-1  bash (For Windows add winpty before docker)
$ /bin/kafka-topics --bootstrap-server localhost:29092 -topic order -create
$ /bin/kafka-topics --list --bootstrap-server localhost:29092
$ /bin/kafka-console-consumer --bootstrap-server localhost:29092 --topic orders --from-beginning

## For Kafka-Mongo Connector
with Docker terminal on connect
$ confluent-hub install --no-prompt mongodb/kafka-connect-mongodb:latest

POST request to  http://localhost:8083/connectors
 with JSON {"name": "mongo-sink-connector",
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
## Compass (MongoDB)
![Compass connection](docs/compass-connection.png)
![Compass data](docs/compass-data.png)

## Credits
[3 Simple Steps to set up Kafka locally using Docker](https://towardsdev.com/3-simple-steps-to-set-up-kafka-locally-using-docker-b07f71f0e2c9)

[Baeldung Kafka](https://www.baeldung.com/ops/kafka-docker-setup)


# Original Requirement (italian)



progetto: gestione di un sushi bar

consegna: 8 settimane divisi in sprint di 2 settimane ciascuno

descrizione: sistema di gestione di un baracchino del sushi virtuale in 
cui ogni cliente può consultare il menù online; una volta registrato
può effettuare l'ordine, indicare il tavolo a cui esssere servito e pagare il conto.
Una volta pagato il conto al cuoco compare la comanda da cucinare e servire
al cliente la sua cena/pranzo.
Un amministratore del locale ha a disposizione una dashboard per gestire gli utenti,
personalizzare e comporre il menù con i prezzi delle portate, supervisionare il 
lavoro del cuoco.

Piano di consegna.

sprint 1
- registrazione utenti (nominativo, email, password) con conferma via mail
- accesso amministratore da backend
- accesso cuoco da backend
- home page con info del ristorante

sprint 2:
- gestione pietanze e costi da parte dell'amministratore (nome, costo, quantità, descrizione, foto)
- navigazione del menù da parte del cliente e possibilità di compore il carrello con le pietanze di cena/pranzo

sprint 3:
- processo di pagamento fake per utenti registrati e invio della comanda al cuoco
- gestione delle comande da parte del cuoco: presa in carico e completamento (fake)
- avviso al cliente (tramite mail) che la sua cena è pronta

sprint 4:
- gestione utenti da parte dell'aministratore
- cambio password per password dimenticata

