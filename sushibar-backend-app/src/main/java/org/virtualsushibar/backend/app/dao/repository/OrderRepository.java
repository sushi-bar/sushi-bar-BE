package org.virtualsushibar.backend.app.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.virtualsushibar.backend.app.dao.entity.OrderEntity;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<OrderEntity, String> {

    Optional<OrderEntity> findByMeal(String meal);

}
