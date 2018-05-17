package org.enricogiurin.sushibar.dto;

import com.google.common.collect.Lists;
import lombok.*;
import org.enricogiurin.sushibar.model.Order;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO {
    @NotNull
    private Long orderId;
    @NotNull
    private LocalDateTime orderCreatedAt;

    private List<Details> details = Lists.newArrayList();

    public static Mapper getMapper() {
        return new Mapper();
    }

    public static class Mapper {
        public OrderDTO map(Order order) {
            OrderDTO result = new OrderDTO();
            result.setOrderId(order.getId());
            result.setOrderCreatedAt(order.getTimeOrder());
            List<Details> details = order.getDetails().stream()
                    .map(orderDetails -> new Details(orderDetails.getQuantity(), orderDetails.getMenu().getName()))
                    .collect(Collectors.toList());
            result.setDetails(details);
            return result;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class Details {
        private int quantity;
        private String item;
    }

}
