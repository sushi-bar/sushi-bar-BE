package org.enricogiurin.sushibar.controller;

import lombok.NonNull;
import org.enricogiurin.sushibar.bo.OrderBO;
import org.enricogiurin.sushibar.dto.OrderDTO;
import org.enricogiurin.sushibar.exception.SBException;
import org.enricogiurin.sushibar.model.Order;
import org.enricogiurin.sushibar.model.OrderStatus;
import org.enricogiurin.sushibar.util.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/cook")
@CrossOrigin
public class CookController {

    @Autowired
    private OrderBO orderBO;

    @GetMapping(value = "pending", produces = "application/json")
    public List<OrderDTO> pendingOrders() {
        List<Order> orders = orderBO.pendingOrders();
        return orders.stream()
                .map(order -> OrderDTO.getMapper().map(order))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "updateOrder/{orderId}", produces = "application/json")
    public ResponseEntity<StringResponse> processOrder(@PathVariable Long orderId, @RequestBody @NonNull OrderStatus orderStatus) {
        if (OrderStatus.COMPLETED.equals(orderStatus)) {
            orderBO.completeOrder(orderId);
            return new ResponseEntity<>(StringResponse.of("orderId " + orderId + " has been completed"), HttpStatus.OK);
        }
        if (OrderStatus.IN_PROGRESS.equals(orderStatus)) {
            orderBO.processOrder(orderId);
            return new ResponseEntity<>(StringResponse.of("orderId " + orderId + " is in progress"), HttpStatus.OK);
        }
        throw new SBException("Invalid order status " + orderStatus);
    }
}
