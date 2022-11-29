package org.virtualsushibar.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

//TODO - evaluate how to run this correctly on circle ci
@DisabledIfSystemProperty(named = "target.env", matches = "circleci")
class TestContainers {
    private static final DockerImageName KAFKA_TEST_IMAGE = DockerImageName.parse("confluentinc/cp-kafka:latest");
    private static final DockerImageName MONGO_TEST_IMAGE = DockerImageName.parse("mongo:4.0.10");
    String bootstrapServers;
    String mongoConnectionString;

    @Test
    void loadKafka(){
        assertThat(bootstrapServers).isNotNull();
        assertThat(mongoConnectionString).isNotNull();
    }

    @BeforeEach
    void beforeAll() {
        try (KafkaContainer kafkaContainer = new KafkaContainer(KAFKA_TEST_IMAGE);
             MongoDBContainer mongoDBContainer = new MongoDBContainer(MONGO_TEST_IMAGE)) {
            kafkaContainer.start();
            mongoDBContainer.start();
            bootstrapServers = kafkaContainer.getBootstrapServers();
            mongoConnectionString = mongoDBContainer.getConnectionString();
        }
    }
}
