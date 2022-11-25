package org.virtualsushibar.backend.service.impl;

import org.springframework.stereotype.Service;
import org.virtualsushibar.backend.avro.Order;

@Service
public interface OrderServiceImpl  {

     void createOrder(Order order);
}
