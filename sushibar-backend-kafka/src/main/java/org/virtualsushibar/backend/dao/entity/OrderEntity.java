package org.virtualsushibar.backend.dao.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class OrderEntity {
    @Id
    public String id;
    public String meal;
    public int amount;
}
