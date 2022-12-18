package org.virtualsushibar.backend.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private String orderConfirmationID;
}
