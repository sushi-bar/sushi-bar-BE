package org.virtualsushibar.backend.app.api.dto;

import lombok.Builder;


@Builder
public record SubmitOrderResponse(String orderConfirmationID) {

}