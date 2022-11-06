package org.virtualsushibar.backend.kafka.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AbstractKafkaConfigs {

    public AbstractKafkaConfigs(@Value(value = "${application.topic.name}") String topic,
                                @Value(value = "${spring.kafka.bootstrap-servers}") String bootstrapAddress) {
        this.topic = topic;
        this.bootstrapAddress = bootstrapAddress;
    }

    private final String topic;
    private final String bootstrapAddress;
}
