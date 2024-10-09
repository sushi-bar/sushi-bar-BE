package org.virtualsushibar.backend.app.dao.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private String orderConfirmationID;
}
