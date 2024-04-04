package org.virtualsushibar.backend.app.kafka.listener;

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
import org.virtualsushibar.backend.app.service.OrderDocumentService;
import org.virtualsushibar.backend.avro.OrderStatus;
import org.virtualsushibar.backend.avro.ProcessedOrder;

//adapted from: https://testcontainers.com/guides/testing-spring-boot-kafka-listener-using-testcontainers/
@SpringBootTest
@DisabledIfSystemProperty(named = "target.env", matches = "circleci")
@TestPropertySource(
    properties = {
        "spring.kafka.consumer.auto-offset-reset=earliest"
    }
)
@Testcontainers
class ProcessedOrderKafkaListenerTest {
  private static final String ORDER_ID = "1";

  @Container
  static final KafkaContainer kafka = new KafkaContainer(
      DockerImageName.parse("confluentinc/cp-kafka:7.3.3")
  );

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
  }

  @MockBean
  OrderDocumentService orderDocumentService;
  @Value("${application.topic.consumer.name}")
  String topic;
  @Autowired
  private KafkaTemplate<String, ProcessedOrder> kafkaTemplate;

  @Test
  void consumeProcessedOrder() {
    ProcessedOrder processedOrder = ProcessedOrder.newBuilder()
        .setOrderStatus(OrderStatus.COMPLETED)
        .setOrderId(ORDER_ID)
        .setCookId("cook")
        .setTimeTaken(1L)
        .build();
    kafkaTemplate.send(topic, "", processedOrder);
    await()
        .pollInterval(Duration.ofSeconds(1))
        .atMost(2, SECONDS)
        .untilAsserted(() -> {
          verify(orderDocumentService).findAndUpdate(ORDER_ID,
              org.virtualsushibar.backend.app.dao.document.OrderStatus.ORDER_PROCESSED);
        });
  }

}