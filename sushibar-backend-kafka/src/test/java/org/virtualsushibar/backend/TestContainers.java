package org.virtualsushibar.backend;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Slf4j
public class TestContainers {
    private String bootstrapServers;

    @Test
    void loadKafka() {
        assertThat(bootstrapServers).isNotNull();
    }

    @BeforeAll
    void beforeAll() {

        try (KafkaContainer kafka = new KafkaContainer
                (DockerImageName.parse("confluentinc/cp-kafka:6.2.1"))) {
            kafka.start();
            bootstrapServers = kafka.getBootstrapServers();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
