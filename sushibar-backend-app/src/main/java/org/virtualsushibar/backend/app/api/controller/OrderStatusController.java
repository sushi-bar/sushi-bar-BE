package org.virtualsushibar.backend.app.api.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.virtualsushibar.backend.app.api.Meal;
import org.virtualsushibar.backend.app.api.dto.Order;
import org.virtualsushibar.backend.app.api.dto.SubmitOrderRequest;
import org.virtualsushibar.backend.app.api.dto.SubmitOrderResponse;
import org.virtualsushibar.backend.app.dao.document.OrderDocument;
import org.virtualsushibar.backend.app.service.CircuitBreakerOrderService;

@RestController
@RequestMapping(OrderStatusController.URL)
@RequiredArgsConstructor
@Slf4j
public class OrderStatusController {

  static final String URL = "/v1/order/status";

  private final CircuitBreakerOrderService orderService;

  @RequestMapping(
      method = {RequestMethod.GET},
      produces = "application/json"
  )
  public ResponseEntity<List<Order>> findAll() {
    List<OrderDocument> all = orderService.findAll();
    List<Order> orders= new ArrayList<>();
    all.forEach(orderDocument -> {
      orders.add(new Order(orderDocument.getOrderId(),
          Meal.valueOf(orderDocument.getMeal()),
          orderDocument.getOrderStatus(),
          orderDocument.getCreatedAt()));
    });
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }
}
