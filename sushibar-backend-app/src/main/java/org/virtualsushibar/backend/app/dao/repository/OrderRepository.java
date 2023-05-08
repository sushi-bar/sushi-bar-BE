package org.virtualsushibar.backend.app.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.virtualsushibar.backend.app.dao.document.OrderDocument;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<OrderDocument, String> {

    Optional<OrderDocument> findByMeal(String meal);

}
