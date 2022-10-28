package org.virtualsushibar.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.virtualsushibar.backend.kafka.configs.AbstractKafkaConfigs;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaServiceProducer {


    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AbstractKafkaConfigs configs;

    public void sendMessage(String message) {
        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(configs.getTopic(), message);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error while publishing message: {}", message, ex);

            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("callback successful when publishing message: {}", message);

            }
        });
    }





}
