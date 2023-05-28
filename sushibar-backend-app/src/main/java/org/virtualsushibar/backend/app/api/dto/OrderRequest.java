package org.virtualsushibar.backend.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.virtualsushibar.backend.app.api.Meals;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Meals meal;
}
