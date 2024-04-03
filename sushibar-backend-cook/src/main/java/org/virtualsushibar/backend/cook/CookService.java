package org.virtualsushibar.backend.cook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.virtualsushibar.backend.avro.Order;
import org.virtualsushibar.backend.avro.OrderStatus;
import org.virtualsushibar.backend.avro.ProcessedOrder;
import org.virtualsushibar.backend.cook.kafka.KafkaProducer;

@Service
@RequiredArgsConstructor
@Slf4j
public class CookService {

  private final KafkaProducer kafkaProducer;

  public void processOrder(Order order) {
    ProcessedOrder processedOrder = ProcessedOrder.newBuilder()
        .setOrderId(order.getOrderId())
        .setOrderStatus(OrderStatus.COMPLETED)
        .setCookId("")
        .setTimeTaken(0L) //to be reviewed
        .build();
    kafkaProducer.sendMessage(processedOrder);
  }

}
