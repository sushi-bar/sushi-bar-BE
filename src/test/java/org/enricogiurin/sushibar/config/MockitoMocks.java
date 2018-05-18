package org.enricogiurin.sushibar.config;

import org.enricogiurin.sushibar.bo.OrderBO;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Defines mockito mocks to be used in the tests.
 */
@Configuration
@Profile("mocks")
public class MockitoMocks {
    @Bean
    @Primary
    public OrderBO mockOrderBO() {
        return Mockito.mock(OrderBO.class);
    }

    @Bean
    @Primary
    public UserDetailsService mockUserDetailsService() {
        return Mockito.mock(UserDetailsService.class);
    }
}
