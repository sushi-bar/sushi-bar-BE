package org.virtualsushibar.backend.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.virtualsushibar.backend.app.api.Meals;
import org.virtualsushibar.backend.app.dao.entity.OrderEntity;
import org.virtualsushibar.backend.app.dao.repository.OrderRepository;
import org.virtualsushibar.backend.app.kafka.producer.order.KafkaOrderProducer;
import org.virtualsushibar.backend.avro.Order;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    OrderService orderService;
    @Mock
    OrderRepository orderRepository;
    @Mock
    KafkaOrderProducer kafkaOrderProducer;

    @Test
    void createOrder() {

        //when
        String confirmationOrder = orderService.createOrder(Meals.SPAGHETTI);
        //then
        verify(orderRepository).save(any(OrderEntity.class));
        verify(kafkaOrderProducer).sendMessage(any(Order.class));
        assertThat(confirmationOrder).isNotEmpty();
        assertThat(UUID.fromString(confirmationOrder).toString()).isEqualTo(confirmationOrder);
    }

    @BeforeEach
    void setUp() {
        this.orderService=new OrderService(orderRepository, kafkaOrderProducer);
    }
}