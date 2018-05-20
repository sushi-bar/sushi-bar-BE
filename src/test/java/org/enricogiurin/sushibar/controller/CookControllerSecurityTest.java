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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Purpose of this test is just to show the integration between mockito and spring-boot.
 */
@ActiveProfiles("mocks")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql("/test-data.sql")
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
    public void pendingOrdersAsAdmin() throws Exception {
        mockMvc.perform(get(URL).with(httpBasic("admin", "aaa"))
                .contentType(contentType))
                .andExpect(status().is(200));
    }

    @Test
    public void pendingOrdersAsCook() throws Exception {
        mockMvc.perform(get(URL).with(httpBasic("cook", "aaa"))
                .contentType(contentType))
                .andExpect(status().is(200));
    }

    @Test
    public void pendingOrdersAsUser() throws Exception {
        mockMvc.perform(get(URL).with(httpBasic("user", "aaa"))
                .contentType(contentType))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }
}