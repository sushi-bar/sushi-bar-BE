package org.virtualsushibar.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.virtualsushibar.backend.api.Meals;
import org.virtualsushibar.backend.avro.Order;
import org.virtualsushibar.backend.dao.entity.OrderEntity;
import org.virtualsushibar.backend.dao.repository.OrderRepository;
import org.virtualsushibar.backend.kafka.producer.KafkaOrderProducer;

import java.util.UUID;


@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService  {
    public static final int DEFAULT_AMOUNT = 1;
    private final OrderRepository orderRepository;
    private final KafkaOrderProducer kafkaOrderProducer;


    @Transactional
    public String createOrder(Meals meal) {
        Order order = Order.newBuilder()
                .setAmount(DEFAULT_AMOUNT)
                .setMeal(meal.name())
                .setOrderId(UUID.randomUUID().toString())
                .build();

        OrderEntity entity = OrderEntity.builder()
                .meal(String.valueOf(meal))
                .amount(DEFAULT_AMOUNT)
                .id(order.getOrderId().toString())
                .build();
        orderRepository.save(entity);
        kafkaOrderProducer.sendMessage(order);
        log.info("Order: {} sent", order.getOrderId());
        return order.getOrderId().toString();
    }
}
