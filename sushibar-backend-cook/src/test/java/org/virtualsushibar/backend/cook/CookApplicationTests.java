package org.virtualsushibar.backend.cook;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest
@Testcontainers
@DisabledIfSystemProperty(named = "target.env", matches = "circleci")
class CookApplicationTests {
	private static final DockerImageName KAFKA_TEST_IMAGE = DockerImageName.parse("confluentinc/cp-kafka:latest");

	@Container
	static KafkaContainer kafkaContainer= new KafkaContainer(KAFKA_TEST_IMAGE);

	@Test
	void contextLoads() {
	}

}
