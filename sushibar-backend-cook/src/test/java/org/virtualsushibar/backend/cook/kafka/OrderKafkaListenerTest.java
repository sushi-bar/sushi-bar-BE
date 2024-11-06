package org.virtualsushibar.backend.cook.kafka;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
import org.virtualsushibar.backend.avro.Order;
import org.virtualsushibar.backend.cook.CookService;

@SpringBootTest
@TestPropertySource(
    properties = {
        "spring.kafka.consumer.auto-offset-reset=earliest"
    }
)
@Testcontainers
class OrderKafkaListenerTest {

  private static final String CONFLUENT_VERSION = "7.6.1";


  private static final Network NETWORK = Network.newNetwork();

  @Container
  static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(
      DockerImageName.parse("confluentinc/cp-kafka:" + CONFLUENT_VERSION)).withNetwork(NETWORK);
  private static final String ORDER_ID = "1";
  @MockBean
  CookService cookService;
  @Value("${application.topic.consumer.name}")
  String topic;
  @Autowired
  private KafkaTemplate<String, Order> kafkaTemplate;

  @Container
  private static final GenericContainer<?> SCHEMA_REGISTRY =
      new GenericContainer<>(
          DockerImageName.parse("confluentinc/cp-schema-registry:" + CONFLUENT_VERSION))
          .withNetwork(NETWORK)
          .withExposedPorts(8081)
          .withEnv("SCHEMA_REGISTRY_HOST_NAME", "schema-registry")
          .withEnv("SCHEMA_REGISTRY_LISTENERS", "http://0.0.0.0:8081")
          .withEnv("SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS",
              "PLAINTEXT://" + KAFKA_CONTAINER.getNetworkAliases().get(0) + ":9092")
          .waitingFor(Wait.forHttp("/subjects").forStatusCode(200));

  private static String getSchemaRegistryUrl() {
    return """
        http://%s:%d
        """.formatted(SCHEMA_REGISTRY.getHost(), SCHEMA_REGISTRY.getFirstMappedPort());
  }

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
    registry.add("spring.kafka.properties.schema.registry.url",
        OrderKafkaListenerTest::getSchemaRegistryUrl);
  }

  @Test
  void consumeOrder() {
    Order order = Order.newBuilder()
        .setOrderId(ORDER_ID)
        .setAmount(1)
        .setMeal("PIZZA")
        .build();
    ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);

    kafkaTemplate.send(topic, "", order);
    await()
        .pollInterval(Duration.ofSeconds(1))
        .atMost(3, SECONDS)
        .untilAsserted(() -> {
          verify(cookService).processOrder(orderArgumentCaptor.capture());
        });
    Order value = orderArgumentCaptor.getValue();
    assertThat(value.getOrderId()).isEqualTo(ORDER_ID);
  }
}

