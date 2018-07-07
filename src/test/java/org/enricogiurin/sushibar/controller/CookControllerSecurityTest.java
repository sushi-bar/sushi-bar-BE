package org.enricogiurin.sushibar.controller;

import com.google.common.collect.Lists;
import org.enricogiurin.sushibar.bo.OrderBO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Purpose of this test is just to show the integration between mockito and spring-boot.
 */
@ActiveProfiles("mocks")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CookControllerSecurityTest {

    private static String URL = "/cook/pending";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderBO mockOrderBO;

    @Before
    public void setup() {
        Mockito.reset(mockOrderBO);
        Mockito.when(mockOrderBO.pendingOrders()).thenReturn(Lists.newArrayList());
    }

    @Test
    @WithMockUser(roles = "COOK")
    public void shouldGetOkWithCookRole() throws Exception {
        mockMvc.perform(get(URL)
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetUnauthorizedWithoutRole() throws Exception {
        mockMvc.perform(get(URL)
                .contentType(contentType))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldGetForbiddenWithUserRole() throws Exception {
        mockMvc.perform(get(URL)
                .contentType(contentType))
                .andExpect(status().isForbidden());
    }
}