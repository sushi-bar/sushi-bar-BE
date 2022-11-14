package org.virtualsushibar.backend.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.virtualsushibar.backend.dao.entity.OrderEntity;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<OrderEntity, String> {

    Optional<OrderEntity> findByMeal(String meal);
}
