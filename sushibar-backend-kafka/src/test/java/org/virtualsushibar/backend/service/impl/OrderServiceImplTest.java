package org.virtualsushibar.backend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.virtualsushibar.backend.avro.Order;
import org.virtualsushibar.backend.dao.repository.OrderRepository;
import org.virtualsushibar.backend.kafka.producer.KafkaOrderProducer;
import org.virtualsushibar.backend.service.OrderService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    OrderService orderService;
    @Mock
    OrderRepository orderRepository;
    @Mock
    KafkaOrderProducer kafkaOrderProducer;

    @Test
    void createOrder() {
        //given
        Order order = Order.newBuilder()
                .setMeal("spaghetti")
                .setOrderId("1")
                .setAmount(2)
                .build();
        //when
        orderService.createOrder(order);
        //then
        verify(orderRepository).save(any());
        verify(kafkaOrderProducer).sendMessage(order);
    }

    @BeforeEach
    void setUp() {
        this.orderService=new OrderServiceImpl(orderRepository, kafkaOrderProducer);
    }
}