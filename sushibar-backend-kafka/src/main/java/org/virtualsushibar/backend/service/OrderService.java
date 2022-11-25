package org.virtualsushibar.backend.service;
import org.virtualsushibar.backend.avro.Order;


public interface OrderService {
     void createOrder(Order order);
}
