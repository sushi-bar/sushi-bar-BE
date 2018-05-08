package org.enricogiurin.sushibar.service;

import org.enricogiurin.sushibar.model.Order;
import org.enricogiurin.sushibar.model.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CookService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> pendingOrders() {
        return orderRepository.pendingOrders();
    }

}
