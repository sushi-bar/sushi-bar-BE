package org.virtualsushibar.backend.app.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.virtualsushibar.backend.app.api.dto.SubmitOrderRequest;
import org.virtualsushibar.backend.app.api.dto.SubmitOrderResponse;
import org.virtualsushibar.backend.app.service.CircuitBreakerOrderService;

@RestController
@RequestMapping(SubmitOrderController.URL)
@RequiredArgsConstructor
@Slf4j
public class SubmitOrderController {

  static final String URL = "/v1/order/submit";

  private final CircuitBreakerOrderService orderService;

  @RequestMapping(
      method = {RequestMethod.POST},
      produces = "application/json"
  )
  public ResponseEntity<SubmitOrderResponse> createOrder(@RequestBody SubmitOrderRequest order) {
    String confirmationOrder = orderService.createOrder(order.meal());
    SubmitOrderResponse submitOrderResponse = SubmitOrderResponse.builder()
        .orderConfirmationID(confirmationOrder)
        .build();
    return new ResponseEntity<>(submitOrderResponse, HttpStatus.CREATED);
  }
}
