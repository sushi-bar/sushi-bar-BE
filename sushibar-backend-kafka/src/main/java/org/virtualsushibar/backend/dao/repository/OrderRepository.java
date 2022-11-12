package org.virtualsushibar.backend.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.virtualsushibar.backend.dao.entity.Order;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<Order> findByMeal(String meal);
}
