package org.virtualsushibar.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class KafkaServiceProducer {


    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topicName;

    public KafkaServiceProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            @Value(value = "${application.topic.name}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    public void sendMessage() {
        String message = System.currentTimeMillis()+"";
        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topicName, message);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error while publishing message: {}", message, ex);

            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.error("callback successful when publishing message: {}", message);

            }
        });
    }





}
