package org.virtualsushibar.backend.kafka.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AbstractKafkaConfigs {

    public AbstractKafkaConfigs(@Value(value = "${application.topic.name}") String topic,
                                @Value(value = "${kafka.bootstrapAddress}") String bootstrapAddress) {
        this.topic = topic;
        this.bootstapAddress = bootstrapAddress;
    }

    private String topic;
    private String bootstapAddress;
}
