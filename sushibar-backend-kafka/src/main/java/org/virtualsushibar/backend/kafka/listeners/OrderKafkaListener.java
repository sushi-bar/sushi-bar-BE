package org.virtualsushibar.backend.kafka.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderKafkaListener {

    @KafkaListener(topics = "${application.topic.name}",groupId = "test")
    public void kafkaListener(String message){
        log.info("Message Received:{}",message);
    }

}
