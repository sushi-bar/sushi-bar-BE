package org.virtualsushibar.backend.app.api.dto;

import lombok.Builder;
import org.virtualsushibar.backend.app.api.Meals;


@Builder
public record OrderRequest(Meals meal) {
}
