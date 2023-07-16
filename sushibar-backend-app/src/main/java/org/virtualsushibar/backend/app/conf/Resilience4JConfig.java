package org.virtualsushibar.backend.app.conf;


import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4JConfig {
    @Bean
    Customizer<Resilience4JCircuitBreakerFactory> customizer(CircuitBreakerConfig circuitBreakerConfig) {
        return factory ->
                factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                        .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3)).build())
                        .circuitBreakerConfig(circuitBreakerConfig)
                        .build());
    }

    @Bean
    CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .build();
    }

    @Bean
    CircuitBreaker circuitBreaker(Resilience4JCircuitBreakerFactory circuitBreakerFactory) {
        return circuitBreakerFactory.create("cb");
    }
}
