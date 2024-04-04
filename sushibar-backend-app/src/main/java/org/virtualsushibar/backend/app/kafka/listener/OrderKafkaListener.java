package org.virtualsushibar.backend.app.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.virtualsushibar.backend.app.dao.document.OrderStatus;
import org.virtualsushibar.backend.app.service.OrderDocumentService;
import org.virtualsushibar.backend.avro.ProcessedOrder;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderKafkaListener {

  private final OrderDocumentService orderDocumentService;

  @KafkaListener(topics = "${application.topic.consumer.name}", groupId = "sb-order-main")
  public void consumeProcessedOrder(ProcessedOrder processedOrder, Acknowledgment acknowledgment) {
    log.info("Message Received: {}", processedOrder);
    //TODO - check the real status of the processed order
    orderDocumentService.findAndUpdate(processedOrder.getOrderId(), OrderStatus.ORDER_PROCESSED);
    acknowledgment.acknowledge();
  }

}
