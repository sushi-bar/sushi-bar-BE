package org.virtualsushibar.backend;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

//TODO - evaluate how to run this correctly on circle ci
@DisabledIfSystemProperty(named = "target.env", matches = "circleci")
class TestContainers {
    private static final DockerImageName KAFKA_TEST_IMAGE = DockerImageName.parse("confluentinc/cp-kafka:latest");
    static String bootstrapServers;

    @Test
    void loadKafka(){
        assertThat(bootstrapServers).isNotNull();
    }

    @BeforeAll
    static void beforeAll() {
        try (KafkaContainer kafka = new KafkaContainer(KAFKA_TEST_IMAGE)) {
            kafka.start();
            bootstrapServers = kafka.getBootstrapServers();
        }
    }
}
