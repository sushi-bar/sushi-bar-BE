package org.enricogiurin.sushibar.bo;

import org.enricogiurin.sushibar.Application;
import org.enricogiurin.sushibar.model.Order;
import org.enricogiurin.sushibar.model.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)

public class OrderBOTest {
    @Autowired
    private OrderBO orderBO;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void pendingOrders() {
        //GIVEN
        assertThat(orderRepository.findAll()).hasSize(2);
        //WHEN
        List<Order> orders = orderBO.pendingOrders();
        //THEN
        assertThat(orders).hasSize(1);
    }
}