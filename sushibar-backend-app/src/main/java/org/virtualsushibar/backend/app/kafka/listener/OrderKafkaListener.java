package org.virtualsushibar.backend.app.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.virtualsushibar.backend.avro.ProcessedOrder;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderKafkaListener {

  @KafkaListener(topics = "${application.topic.consumer.name}", groupId = "sb-order-main")
  public void kafkaListener(ProcessedOrder processedOrder, Acknowledgment acknowledgment) {
    log.info("Message Received: {}", processedOrder);
    acknowledgment.acknowledge();
  }

}
