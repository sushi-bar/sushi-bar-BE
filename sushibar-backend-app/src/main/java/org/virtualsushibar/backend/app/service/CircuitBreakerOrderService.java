package org.virtualsushibar.backend.app.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.virtualsushibar.backend.app.api.Meal;
import org.virtualsushibar.backend.app.dao.document.OrderDocument;
import org.virtualsushibar.backend.app.exception.SystemUnavailableException;


@RequiredArgsConstructor
@Slf4j
@Service
public class CircuitBreakerOrderService {


  private final Resilience4JCircuitBreakerFactory resilience4JCircuitBreakerFactory;
  private final OrderService orderService;


  public String createOrder(Meal meal) {
    CircuitBreaker circuitBreaker = resilience4JCircuitBreakerFactory.create("cb");
    return circuitBreaker.run(() -> orderService.createOrder(meal), this::getDefault);
  }

  public List<OrderDocument> findAll() {
    return orderService.findAll();
  }

  public String getDefault(Throwable t) {
    throw new SystemUnavailableException(t);
  }


}
