package org.virtualsushibar.backend.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.virtualsushibar.backend.app.api.Meals;
import org.virtualsushibar.backend.app.dao.document.OrderDocument;
import org.virtualsushibar.backend.app.dao.repository.OrderRepository;
import org.virtualsushibar.backend.app.kafka.producer.KafkaProducer;
import org.virtualsushibar.backend.avro.Order;

import java.util.UUID;

import static org.virtualsushibar.backend.app.dao.document.OrderStatus.ORDER_NOT_CONFIRMED;


@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {
    public static final int DEFAULT_AMOUNT = 1;
    private final OrderRepository orderRepository;
    private final KafkaProducer kafkaOrderProducer;


    @Transactional
    public String createOrder(Meals meal) {
        UUID uuid = UUID.randomUUID();
        Order order = Order.newBuilder()
                .setAmount(DEFAULT_AMOUNT)
                .setMeal(meal.name())
                .setOrderId(uuid.toString())
                .build();

        OrderDocument document = OrderDocument.builder()
                .meal(String.valueOf(meal))
                .amount(DEFAULT_AMOUNT)
                .orderId(uuid.toString())
                .orderStatus(ORDER_NOT_CONFIRMED)
                .build();

        OrderDocument save = orderRepository.save(document);
        log.info("Order saved in mongoDB with id: {}", save.getId());
        kafkaOrderProducer.sendMessage(order);
        log.info("Order: {} sent", order.getOrderId());
        return order.getOrderId();
    }
}
