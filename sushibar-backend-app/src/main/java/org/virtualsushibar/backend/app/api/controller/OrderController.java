package org.virtualsushibar.backend.app.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.virtualsushibar.backend.app.api.dto.OrderRequest;
import org.virtualsushibar.backend.app.api.dto.OrderResponse;
import org.virtualsushibar.backend.app.service.OrderService;

@RestController
@RequestMapping(OrderController.URL)
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    static final String URL = "/v1/order";

    private final OrderService orderService;
    @RequestMapping(
            method = {RequestMethod.POST},
            produces = "application/json"
    )
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest order) {
        String confirmationOrder = orderService.createOrder(order.getMeal());
        OrderResponse orderResponse = OrderResponse.builder()
                .orderConfirmationID(confirmationOrder)
                .build();
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }
}
