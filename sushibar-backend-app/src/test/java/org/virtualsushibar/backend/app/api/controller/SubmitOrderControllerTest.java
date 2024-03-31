package org.virtualsushibar.backend.app.api.controller;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.virtualsushibar.backend.app.api.Meal;
import org.virtualsushibar.backend.app.api.dto.SubmitOrderRequest;
import org.virtualsushibar.backend.app.service.CircuitBreakerOrderService;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {SubmitOrderController.class})
class SubmitOrderControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CircuitBreakerOrderService orderService;

  @Test
  void createOrder() throws Exception {
    SubmitOrderRequest submitOrderRequest = orderRequest();
    //when-then
    this.mockMvc.perform(post(SubmitOrderController.URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(submitOrderRequest)))
        .andExpect(status().isCreated());
    verify(orderService).createOrder(Meal.PIZZA);
  }

  private SubmitOrderRequest orderRequest() {
    return SubmitOrderRequest.builder()
        .meal(Meal.PIZZA)
        .build();
  }
}