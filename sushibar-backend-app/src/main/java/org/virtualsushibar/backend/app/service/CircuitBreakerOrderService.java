package org.virtualsushibar.backend.app.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.virtualsushibar.backend.app.api.Meals;
import org.virtualsushibar.backend.app.exception.SystemUnavailableException;


@RequiredArgsConstructor
@Slf4j
@Service
public class CircuitBreakerOrderService {


    private final Resilience4JCircuitBreakerFactory resilience4JCircuitBreakerFactory;
    private final OrderService orderService;


    public String createOrder(Meals meal) {
        CircuitBreaker circuitBreaker = resilience4JCircuitBreakerFactory.create("cb");
        return circuitBreaker.run(() -> orderService.createOrder(meal), this::getDefault);
    }

    public String getDefault(Throwable t) {
        throw new SystemUnavailableException(t);
    }


}
