package org.virtualsushibar.backend.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

//TODO - evaluate how to run this correctly on circle ci
@DisabledIfSystemProperty(named = "target.env", matches = "circleci")
class TestContainers {
    private static final DockerImageName KAFKA_TEST_IMAGE = DockerImageName.parse("confluentinc/cp-kafka:latest");
    String bootstrapServers;

    @Test
    void loadKafka(){
        assertThat(bootstrapServers).isNotNull();
    }

    @BeforeEach
    void beforeAll() {
        try (KafkaContainer kafkaContainer = new KafkaContainer(KAFKA_TEST_IMAGE)) {
            kafkaContainer.start();
            bootstrapServers = kafkaContainer.getBootstrapServers();
        }
    }
}
