package org.virtualsushibar.backend.api.dto;

import lombok.Data;
import org.virtualsushibar.backend.api.Meals;

@Data
public class OrderRequest {
    private Meals meal;
}
