package org.virtualsushibar.backend.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
@DisabledIfSystemProperty(named = "target.env", matches = "circleci")
class MainApplicationTests {

  private static final DockerImageName KAFKA_TEST_IMAGE = DockerImageName.parse(
      "confluentinc/cp-kafka:latest");
  private static final DockerImageName MONGO_TEST_IMAGE = DockerImageName.parse("mongo:latest");
  @Container
  static MongoDBContainer mongoDBContainer = new MongoDBContainer(MONGO_TEST_IMAGE);
  @Container
  static KafkaContainer kafkaContainer = new KafkaContainer(KAFKA_TEST_IMAGE);

  @DynamicPropertySource
  static void kafkaProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers",
        () -> kafkaContainer.getHost() + ":" + kafkaContainer.getFirstMappedPort());
    registry.add("spring.kafka.admin.properties.min-in-sync-replicas", () -> 1);
    registry.add("spring.kafka.admin.properties.number-of-replicas", () -> 1);
  }


  @Test
  void contextLoads() {
  }

}
