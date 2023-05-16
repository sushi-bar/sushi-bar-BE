package org.virtualsushibar.backend.app.dao.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.virtualsushibar.backend.app.dao.document.OrderDocument;
import org.virtualsushibar.backend.app.dao.document.OrderStatus;

import javax.annotation.Resource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//https://github.com/rieckpil/blog-tutorials/blob/master/spring-data-mongo-test-testcontainers/src/test/java/de/rieckpil/blog/CustomerRepositoryTest.java
@DisabledIfSystemProperty(named = "target.env", matches = "circleci")
@Testcontainers
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class OrderRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }


    @Resource
    private OrderRepository orderRepository;

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