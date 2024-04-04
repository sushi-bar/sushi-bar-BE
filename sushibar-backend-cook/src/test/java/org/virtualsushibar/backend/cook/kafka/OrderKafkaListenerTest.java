package org.virtualsushibar.backend.cook.kafka;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.virtualsushibar.backend.avro.Order;
import org.virtualsushibar.backend.cook.CookService;

@SpringBootTest
@DisabledIfSystemProperty(named = "target.env", matches = "circleci")
@TestPropertySource(
    properties = {
        "spring.kafka.consumer.auto-offset-reset=earliest"
    }
)
@Testcontainers
class OrderKafkaListenerTest {

  @Container
  static final KafkaContainer kafka = new KafkaContainer(
      DockerImageName.parse("confluentinc/cp-kafka:7.3.3")
  );
  private static final String ORDER_ID = "1";
  @MockBean
  CookService cookService;
  @Value("${application.topic.consumer.name}")
  String topic;
  @Autowired
  private KafkaTemplate<String, Order> kafkaTemplate;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
  }

  @Test
  void consumeOrder() {
    Order order = Order.newBuilder()
        .setOrderId(ORDER_ID)
        .setAmount(1)
        .setMeal("PIZZA")
        .build();

    kafkaTemplate.send(topic, "", order);
    await()
        .pollInterval(Duration.ofSeconds(1))
        .atMost(2, SECONDS)
        .untilAsserted(() -> {
          verify(cookService).processOrder(order);
        });
  }
}

