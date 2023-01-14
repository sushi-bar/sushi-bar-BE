package org.virtualsushibar.backend.app.dao.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class OrderEntity {
    @Id
    public String id;
    public String meal;
    public int amount;
}
