package org.virtualsushibar.backend.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.virtualsushibar.backend.avro.Order;
import org.virtualsushibar.backend.kafka.config.AbstractKafkaConfigs;
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaOrderProducer {
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final AbstractKafkaConfigs configs;

    public void sendMessage(Order order) throws KafkaException {
        ListenableFuture<SendResult<String, Order>> listenableFuture = kafkaTemplate.send(configs.getTopic(), order);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error while publishing message: {}", order, ex);

            }
            @Override
            public void onSuccess(SendResult<String, Order> result) {
                log.info("callback successful when publishing message: {}", order);

            }
        });
    }





}
