package org.virtualsushibar.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.virtualsushibar.backend.avro.Order;
import org.virtualsushibar.backend.dao.entity.OrderEntity;
import org.virtualsushibar.backend.dao.repository.OrderRepository;
import org.virtualsushibar.backend.kafka.producer.KafkaOrderProducer;
import org.virtualsushibar.backend.service.impl.OrderServiceImpl;


@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService implements OrderServiceImpl {
    private final OrderRepository orderRepository;
    private final KafkaOrderProducer kafkaOrderProducer;


    @Transactional
    public void createOrder(Order order) {
        OrderEntity entity = OrderEntity.builder()
                .meal(String.valueOf(order.getMeal()))
                .build();
        orderRepository.save(entity);
        kafkaOrderProducer.sendMessage(order);
        log.info("Order: {} sent", order.getOrderId());
    }
}
