package org.virtualsushibar.backend.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Order {
    @Id
    public String id;
    public String meal;
}
