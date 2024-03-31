package org.virtualsushibar.backend.app.dao.document;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("order")
public class OrderDocument {

  @Id
  private String id;
  private String orderId;
  private String meal;
  private int amount;
  private OrderStatus orderStatus;
  private Instant createdAt;
}
