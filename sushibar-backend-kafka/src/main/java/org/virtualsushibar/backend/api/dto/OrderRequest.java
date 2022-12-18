package org.virtualsushibar.backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.virtualsushibar.backend.api.Meals;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private  Meals meal;
}
