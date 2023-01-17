package org.virtualsushibar.backend.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
@DisabledIfSystemProperty(named = "target.env", matches = "circleci")
class MainApplicationTests {

	private static final DockerImageName KAFKA_TEST_IMAGE = DockerImageName.parse("confluentinc/cp-kafka:latest");
	private static final DockerImageName MONGO_TEST_IMAGE = DockerImageName.parse("mongo:latest");
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer(MONGO_TEST_IMAGE);
	@Container
	static KafkaContainer kafkaContainer= new KafkaContainer(KAFKA_TEST_IMAGE);




	@Test
	void contextLoads() {
	}

}
