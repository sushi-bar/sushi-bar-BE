package org.enricogiurin.sushibar.model.repository;

import org.enricogiurin.sushibar.model.Order;
import org.enricogiurin.sushibar.util.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.annotation.Secured;

import java.util.List;


@Secured(value = {Role.ROLE_USER})
public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> pendingOrders();

}
