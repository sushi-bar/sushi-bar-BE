package org.virtualsushibar.backend.app.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CompletableFuture;


@RequiredArgsConstructor
@Component
@Slf4j
public class AbstractKafkaProducer<S, E extends SpecificRecord> {

    private final KafkaTemplate<S, E> kafkaTemplate;

    public void sendMessage(String topic, E event) {
        CompletableFuture<SendResult<S, E>> completableFuture = kafkaTemplate
                .send(topic, event);
        completableFuture.whenComplete((seSendResult, ex) -> {

            if (ex == null) {
                log.info("callback successful when publishing message: {}", event);
            } else {
                log.error("Error while publishing message: {}", event, ex);
                throw new KafkaException(ex.getMessage());
            }
        });

    }
}
