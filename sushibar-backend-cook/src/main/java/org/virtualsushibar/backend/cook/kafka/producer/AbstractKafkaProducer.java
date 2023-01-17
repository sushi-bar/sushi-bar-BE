package org.virtualsushibar.backend.cook.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


@RequiredArgsConstructor
@Component
@Slf4j
public class AbstractKafkaProducer<S,E extends SpecificRecord> {

    private final KafkaTemplate<S, E> kafkaTemplate;

    public void sendMessage(String topic, E event) {
    ListenableFuture<SendResult<S, E>> listenableFuture = kafkaTemplate
            .send(topic, event);
        listenableFuture.addCallback(new ListenableFutureCallback<>()

    {
        @Override
        public void onFailure (Throwable ex){
        log.error("Error while publishing message: {}", event, ex);
        throw new KafkaException(ex.getMessage());

    }
        @Override
        public void onSuccess (SendResult < S, E > result){
        log.info("callback successful when publishing message: {}", event);

    }
    });
}
}
