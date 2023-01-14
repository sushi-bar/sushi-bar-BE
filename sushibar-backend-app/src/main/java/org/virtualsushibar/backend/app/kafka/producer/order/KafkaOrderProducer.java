package org.virtualsushibar.backend.app.kafka.producer.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Service;
import org.virtualsushibar.backend.avro.Order;
import org.virtualsushibar.backend.app.kafka.config.AbstractKafkaConfigs;
import org.virtualsushibar.backend.app.kafka.producer.AbstractKafkaProducer;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaOrderProducer {


    private final AbstractKafkaProducer<String, Order> kafkaProducer;
    private final AbstractKafkaConfigs configs;

    public void sendMessage(Order order) {

        kafkaProducer.sendMessage(configs.getProducerTopic(), order);


    }


}
