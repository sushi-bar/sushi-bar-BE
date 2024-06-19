package org.virtualsushibar.backend.app.dao.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.virtualsushibar.backend.app.dao.document.OrderDocument;
import org.virtualsushibar.backend.app.dao.document.OrderStatus;

//https://github.com/rieckpil/blog-tutorials/blob/master/spring-data-mongo-test-testcontainers/src/test/java/de/rieckpil/blog/CustomerRepositoryTest.java
@Testcontainers
@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class OrderRepositoryTest {

  @Container
  static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
  @Autowired
  private OrderRepository orderRepository;

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @Test
  void findByOrderId() {
    //when
    OrderDocument order = orderRepository.findByOrderId("1").orElseThrow(RuntimeException::new);

    //then
    assertThat(order).isNotNull();
    assertThat(order.getMeal()).isEqualTo("pizza");
  }


  @AfterEach
  void cleanUp() {
    this.orderRepository.deleteAll();
  }

  @BeforeEach
  void setUp() {
    OrderDocument orderDocument = OrderDocument.builder()
        .orderId("1")
        .meal("pizza")
        .orderStatus(OrderStatus.ORDER_CONFIRMED)
        .build();
    this.orderRepository.save(orderDocument);
  }
}