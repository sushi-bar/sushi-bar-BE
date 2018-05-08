package org.enricogiurin.sushibar.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "SB_ORDER")
@NamedQueries(value = {@NamedQuery(name = "Order.pendingOrders", query =
        "select o from Order o where o.status = org.enricogiurin.sushibar.model.OrderStatus.CREATED")})

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Customer customer;

    @Column
    private LocalDateTime timeOrder;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany
    private Set<OrderDetails> orderDetailsSet;

}
