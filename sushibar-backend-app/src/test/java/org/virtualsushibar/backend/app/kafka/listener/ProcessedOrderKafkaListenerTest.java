package org.virtualsushibar.backend.app.kafka.listener;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.virtualsushibar.backend.app.service.OrderDocumentService;
import org.virtualsushibar.backend.avro.OrderStatus;
import org.virtualsushibar.backend.avro.ProcessedOrder;

//adapted from: https://testcontainers.com/guides/testing-spring-boot-kafka-listener-using-testcontainers/
@SpringBootTest
@TestPropertySource(
    properties = {
        "spring.kafka.consumer.auto-offset-reset=earliest"
    }
)
@Testcontainers
class ProcessedOrderKafkaListenerTest {
  private static final String ORDER_ID = "1";
  private static final String CONFLUENT_VERSION = "7.6.1";

  private static final Network NETWORK = Network.newNetwork();


  @Container
  static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(
      DockerImageName.parse("confluentinc/cp-kafka:"+CONFLUENT_VERSION)).withNetwork(NETWORK);

  @Container
  private static final GenericContainer<?> SCHEMA_REGISTRY =
      new GenericContainer<>(DockerImageName.parse("confluentinc/cp-schema-registry:"+CONFLUENT_VERSION))
          .withNetwork(NETWORK)
          .withExposedPorts(8081)
          .withEnv("SCHEMA_REGISTRY_HOST_NAME", "schema-registry")
          .withEnv("SCHEMA_REGISTRY_LISTENERS", "http://0.0.0.0:8081")
          .withEnv("SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS",
              "PLAINTEXT://" + KAFKA_CONTAINER.getNetworkAliases().get(0) + ":9092")
          .waitingFor(Wait.forHttp("/subjects").forStatusCode(200));

  private static String getSchemaRegistryUrl() {
    return "http://" + SCHEMA_REGISTRY.getHost() + ":" + SCHEMA_REGISTRY.getFirstMappedPort();
  }

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
    registry.add("spring.kafka.properties.schema.registry.url", ProcessedOrderKafkaListenerTest::getSchemaRegistryUrl);
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