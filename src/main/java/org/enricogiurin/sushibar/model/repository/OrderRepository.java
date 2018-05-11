package org.enricogiurin.sushibar.model.repository;

import org.enricogiurin.sushibar.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> pendingOrders();

}
