package org.virtualsushibar.backend.app.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.virtualsushibar.backend.app.dao.document.OrderStatus;
import org.virtualsushibar.backend.app.service.OrderDocumentService;
import org.virtualsushibar.backend.avro.Order;

import java.util.concurrent.CompletableFuture;


@Component
@Slf4j
public class KafkaProducer {
    //TODO - fix this
    static final String KEY = "KEY";

    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final OrderDocumentService orderDocumentService;
    private final String topic;

    public KafkaProducer(KafkaTemplate<String, Order> kafkaTemplate,
                         @Value("${application.topic.producer.name}") String topic,
                         OrderDocumentService orderDocumentService) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.orderDocumentService = orderDocumentService;
    }

    public void sendMessage(Order order) {

        CompletableFuture<SendResult<String, Order>> future = kafkaTemplate.send(topic, KEY, order);

        future.thenAccept(result -> {
                    log.info("callback successful when publishing message: {}", order);
                    orderDocumentService.findAndUpdate(order.getOrderId(), OrderStatus.ORDER_CONFIRMED);
                })
                .exceptionally(ex -> {
                    log.error("Error while publishing message: {}", order, ex);
                    orderDocumentService.findAndUpdate(order.getOrderId(), OrderStatus.TECHNICAL_FAILURE);
                    return null; // or handle the exception in a different way
                });
    }


}
