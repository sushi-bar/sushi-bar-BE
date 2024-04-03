package org.virtualsushibar.backend.cook.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.virtualsushibar.backend.avro.Order;
import org.virtualsushibar.backend.cook.CookService;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderKafkaListener {

  private final CookService cookService;

  @KafkaListener(topics = "${application.topic.consumer.name}", groupId = "sb-order-main")
  public void kafkaListener(Order order, Acknowledgment acknowledgment) {
    log.info("Message Received:{}", order);
    cookService.processOrder(order);
    acknowledgment.acknowledge();
  }

}
