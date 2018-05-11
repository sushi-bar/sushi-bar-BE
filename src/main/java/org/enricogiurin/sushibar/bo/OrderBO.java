package org.enricogiurin.sushibar.bo;

import org.enricogiurin.sushibar.model.Order;
import org.enricogiurin.sushibar.model.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderBO {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public List<Order> pendingOrders() {
        return orderRepository.pendingOrders();
    }

}
