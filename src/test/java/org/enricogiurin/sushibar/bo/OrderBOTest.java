package org.enricogiurin.sushibar.bo;

import org.enricogiurin.sushibar.Application;
import org.enricogiurin.sushibar.model.Order;
import org.junit.Before;
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

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void pendingOrders() {
        List<Order> orders = orderBO.pendingOrders();
        assertThat(orders).hasSize(1);
    }
}