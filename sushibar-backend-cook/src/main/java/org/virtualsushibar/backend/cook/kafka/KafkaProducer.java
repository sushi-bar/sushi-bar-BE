package org.virtualsushibar.backend.cook.kafka;

import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.virtualsushibar.backend.avro.ProcessedOrder;


@Component
@Slf4j
public class KafkaProducer {

  //TODO - fix this
  static final String KEY = "KEY";

  private final KafkaTemplate<String, ProcessedOrder> kafkaTemplate;
  private final String topic;

  public KafkaProducer(KafkaTemplate<String, ProcessedOrder> kafkaTemplate,
      @Value("${application.topic.producer.name}") String topic) {
    this.kafkaTemplate = kafkaTemplate;
    this.topic = topic;
  }

  public void sendMessage(ProcessedOrder order) {

    CompletableFuture<SendResult<String, ProcessedOrder>> future = kafkaTemplate.send(topic, KEY,
        order);

    future.thenAccept(result -> {
          log.info("callback successful when publishing message: {}", order);
        })
        .exceptionally(ex -> {
          log.error("Error while publishing message: {}", order, ex);
          return null; // or handle the exception in a different way
        });
  }


}
