package org.enricogiurin.sushibar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.enricogiurin.sushibar.Application;
import org.enricogiurin.sushibar.model.UserRepository;
import org.enricogiurin.sushibar.po.UserDTO;
import org.enricogiurin.sushibar.util.EmailSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.mockito.BDDMockito.when;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by enrico on 7/16/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, RegistrationControllerTest.TestConfig.class})
public class RegistrationControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        when(userRepository.findByEmail(anyString())).thenReturn(Arrays.asList());
    }

    @Test
    public void register() throws Exception {
        mockMvc.perform(
                post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new UserDTO("enrico", "enrico@enricogiurin.org"))))
                .andExpect(status().isOk());

    }

    @Configuration
    static class TestConfig {
        @Bean
        @Primary
        public EmailSender emailSender() {
            return new EmailSender() {
                @Override
                public void sendSimpleMessage(String to, String subject, String text) {
                    //TODO use this class to extrapolate the url sent by mail.
                }
            };
        }
    }


}