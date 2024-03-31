package org.virtualsushibar.backend.app.dao.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.virtualsushibar.backend.app.dao.document.OrderDocument;

public interface OrderRepository extends MongoRepository<OrderDocument, String> {

  Optional<OrderDocument> findByOrderId(String orderId);

}
