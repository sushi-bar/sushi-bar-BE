package org.virtualsushibar.backend.cook.kafka.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AbstractKafkaConfigs {

    private final String consumerTopic;
    private final String bootstrapAddress;
    public AbstractKafkaConfigs(@Value(value = "${application.topic.consumer.name}") String consumerTopic,
                                @Value(value = "${spring.kafka.bootstrap-servers}") String bootstrapAddress) {
        this.consumerTopic = consumerTopic;
        this.bootstrapAddress = bootstrapAddress;
    }
}
