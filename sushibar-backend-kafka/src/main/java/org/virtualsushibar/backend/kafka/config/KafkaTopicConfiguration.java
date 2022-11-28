package org.virtualsushibar.backend.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic compactTopicExample(@Value(value = "${application.topic.name}") String topic,
                                        @Value(value = "${application.topic.partitions:1}") int numberOfPartitions) {
        return TopicBuilder.name(topic)
                .partitions(numberOfPartitions)
                .replicas(3)
                .compact()
                .build();
    }
}
