package org.virtualsushibar.backend.app.kafka.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AbstractKafkaConfigs {

    public AbstractKafkaConfigs(@Value(value = "${application.topic.producer.name}") String producerTopic,
                                @Value(value = "${application.topic.consumer.name}") String consumerTopic) {
        this.producerTopic = producerTopic;
        this.consumerTopic=consumerTopic;
    }

    private final String producerTopic;
    private final String consumerTopic;
}
