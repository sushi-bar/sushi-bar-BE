package org.virtualsushibar.backend.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.virtualsushibar.backend.avro.Order;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderKafkaListener {

    @KafkaListener(topics = "${application.topic.name}",groupId = "sb-order-main")
    public void kafkaListener(Order order, Acknowledgment acknowledgment){
        log.info("Message Received:{}",order);
        acknowledgment.acknowledge();
    }

}
