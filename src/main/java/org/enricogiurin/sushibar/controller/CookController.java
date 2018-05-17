package org.enricogiurin.sushibar.controller;

import org.enricogiurin.sushibar.bo.OrderBO;
import org.enricogiurin.sushibar.dto.OrderDTO;
import org.enricogiurin.sushibar.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
