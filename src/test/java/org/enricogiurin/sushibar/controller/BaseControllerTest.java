package org.enricogiurin.sushibar.controller;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by enrico on 7/22/17.
 */
public abstract class BaseControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected Filter springSecurityFilterChain;

    protected MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext)
                .addFilters(springSecurityFilterChain)
                .build();
    }
}