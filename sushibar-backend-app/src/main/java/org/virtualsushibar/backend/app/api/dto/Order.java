package org.virtualsushibar.backend.app.api.dto;

import java.time.Instant;
import org.virtualsushibar.backend.app.api.Meal;
import org.virtualsushibar.backend.app.dao.document.OrderStatus;

public record Order(String orderConfirmationID, Meal meal, OrderStatus orderStatus, Instant createdAt) {

}
