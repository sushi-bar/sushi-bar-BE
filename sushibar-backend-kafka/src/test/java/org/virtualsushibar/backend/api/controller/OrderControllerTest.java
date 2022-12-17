package org.virtualsushibar.backend.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.virtualsushibar.backend.api.Meals;
import org.virtualsushibar.backend.api.dto.OrderRequest;
import org.virtualsushibar.backend.service.OrderService;

import javax.annotation.Resource;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {OrderController.class})
class OrderControllerTest {


    @Resource
    private MockMvc mockMvc;

    @Resource
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void createOrder() throws Exception {
        OrderRequest orderRequest = orderRequest();
        //when-then
        this.mockMvc.perform(post(OrderController.URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated());
        verify(orderService).createOrder(Meals.PIZZA);
    }

    private OrderRequest orderRequest(){
        return OrderRequest.builder()
                .meal(Meals.PIZZA)
                .build();
    }
}