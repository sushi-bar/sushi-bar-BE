package org.virtualsushibar.backend.cook.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.virtualsushibar.backend.avro.Order;
import org.virtualsushibar.backend.avro.ProcessedOrder;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderKafkaListener {

    @KafkaListener(topics = "${application.topic.consumer.name}",groupId = "sb-order-main")
    public void kafkaListener(Order order, Acknowledgment acknowledgment){
        log.info("Message Received:{}",order);


        /*
        //TODO add Process service
        //TODO Receive Order and assign the Idle COOK
        //TODO Wait for the cook to complete teh ORDER
        //TODO once Order is complete, CooK will Trigger the REST to SEND back to main APP

         */

        acknowledgment.acknowledge();
    }

}
