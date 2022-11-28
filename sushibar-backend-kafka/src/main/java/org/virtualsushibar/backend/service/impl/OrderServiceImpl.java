package org.virtualsushibar.backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.virtualsushibar.backend.avro.Order;
import org.virtualsushibar.backend.dao.entity.OrderEntity;
import org.virtualsushibar.backend.dao.repository.OrderRepository;
import org.virtualsushibar.backend.kafka.producer.KafkaOrderProducer;
import org.virtualsushibar.backend.service.OrderService;


@RequiredArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final KafkaOrderProducer kafkaOrderProducer;


    @Transactional
    public void createOrder(Order order) {
        OrderEntity entity = OrderEntity.builder()
                .meal(String.valueOf(order.getMeal()))
                .amount(order.getAmount())
                .build();
        orderRepository.save(entity);
        kafkaOrderProducer.sendMessage(order);
        log.info("Order: {} sent", order.getOrderId());
    }
}
