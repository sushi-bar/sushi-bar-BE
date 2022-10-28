package org.virtualsushibar.backend.kafka.configs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@RequiredArgsConstructor
@Configuration
public class AbstractKafkaConfigs {


    @Value(value = "${application.topic.name}")
    private  String topic;
    @Value(value = "${kafka.bootstrapAddress}")
    private  String bootstapAddress;
}
