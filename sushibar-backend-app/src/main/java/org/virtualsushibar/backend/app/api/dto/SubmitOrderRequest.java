package org.virtualsushibar.backend.app.api.dto;

import lombok.Builder;
import org.virtualsushibar.backend.app.api.Meal;


@Builder
public record SubmitOrderRequest(Meal meal) {

}
