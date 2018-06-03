package org.enricogiurin.sushibar.bo;

import org.enricogiurin.sushibar.exception.SBException;
import org.enricogiurin.sushibar.model.Order;
import org.enricogiurin.sushibar.model.OrderStatus;
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

    @Transactional
    public void processOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new SBException("OrderID " + orderId + " not found"));
        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            throw new SBException("Invalid status of order " + orderId + " - " + order.getStatus());
        }
        order.setStatus(OrderStatus.IN_PROGRESS);
    }

    @Transactional
    public void completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new SBException("OrderID " + orderId + " not found"));
        if (!order.getStatus().equals(OrderStatus.IN_PROGRESS)) {
            throw new SBException("Invalid status of order " + orderId + " - " + order.getStatus());
        }
        order.setStatus(OrderStatus.COMPLETED);
    }
}
