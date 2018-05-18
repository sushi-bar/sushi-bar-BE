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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.Collections;

import static org.mockito.internal.verification.VerificationModeFactory.times;
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
public class CookControllerTest {

    private static String URL = "/cook/pending";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderBO mockOrderBO;

    @Autowired
    private UserDetailsService mockUserDetailsService;

    @Before
    public void setup() {
        SimpleGrantedAuthority admin = new SimpleGrantedAuthority("ROLE_ADMIN");
        User user = new User("admin", "$2a$10$B/dmAz4IXat0ieO0s6pRkuOY1kelPOn2YNLJqYfdqA96dof85Y9D2",
                true, true, true, true, Collections.singletonList(admin));
        Mockito.when(mockOrderBO.pendingOrders()).thenReturn(Lists.newArrayList());
        Mockito.when(mockUserDetailsService.loadUserByUsername("admin")).thenReturn(user);
    }


    @Test
    public void pendingOrders() throws Exception {
        mockMvc.perform(get(URL).with(httpBasic("admin", "aaa"))
                .contentType(contentType))
                .andExpect(status().is(200));
        Mockito.verify(mockUserDetailsService, times(1)).loadUserByUsername("admin");
    }


}