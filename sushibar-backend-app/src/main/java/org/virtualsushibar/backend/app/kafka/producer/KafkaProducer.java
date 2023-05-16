package org.virtualsushibar.backend.app.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.virtualsushibar.backend.app.dao.document.OrderStatus;
import org.virtualsushibar.backend.app.dao.repository.OrderRepository;
import org.virtualsushibar.backend.app.service.OrderDocumentService;
import org.virtualsushibar.backend.avro.Order;


@Component
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final OrderDocumentService orderDocumentService;
    private final String topic;

    public KafkaProducer(KafkaTemplate<String, Order> kafkaTemplate,
                         @Value("appplication.topic.producer.name") String topic,
                         OrderDocumentService orderDocumentService) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.orderDocumentService = orderDocumentService;
    }

    public void sendMessage(Order order) {
        ListenableFuture<SendResult<String, Order>> listenableFuture = kafkaTemplate
                .send(topic,  order);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error while publishing message: {}", order, ex);
                orderDocumentService.findAndUpdate((String) order.getOrderId(), OrderStatus.TECHNICAL_FAILURE);
            }

            @Override
            public void onSuccess(SendResult<String, Order> result) {
                log.info("callback successful when publishing message: {}", order);
                orderDocumentService.findAndUpdate((String) order.getOrderId(), OrderStatus.ORDER_CONFIRMED);
            }
        });
    }
}
