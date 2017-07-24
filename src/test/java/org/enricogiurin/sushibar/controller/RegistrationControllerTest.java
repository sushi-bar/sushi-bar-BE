package org.enricogiurin.sushibar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.enricogiurin.sushibar.Application;
import org.enricogiurin.sushibar.po.UserDTO;
import org.enricogiurin.sushibar.util.EmailSender;
import org.enricogiurin.sushibar.util.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.enricogiurin.sushibar.controller.RegistrationControllerTest.TestConfig.map;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by enrico on 7/16/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, RegistrationControllerTest.TestConfig.class})
public class RegistrationControllerTest extends BaseControllerTest {

    private static String EMAIL_TO = "enrico@enricogiurin.org";
    private static String URL_REGISTRATION = "/registration";
    private static String USERNAME = "enrico";


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setup() throws Exception {
        super.setup();
    }

    @Test
    public void register() throws Exception {
        mockMvc.perform(
                post(URL_REGISTRATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new UserDTO(USERNAME, EMAIL_TO))))
                .andExpect(status().isOk());

        assertEquals(EMAIL_TO, map.get("to"));
        String token = map.get("text")
                .substring(map.get("text").indexOf("e=") + 2);
        String url = Utils.buildURL(URL_REGISTRATION, map.get("to"), token);
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

    }

    @Configuration
    static class TestConfig {
        static Map<String, String> map = new HashMap<>();
        @Bean
        @Primary
        public EmailSender emailSender() {
            return new EmailSender() {
                @Override
                public void sendSimpleMessage(String to, String subject, String text) {
                    //TODO use this class to extrapolate the url sent by mail.
                    map.put("to", to);
                    map.put("text", text);
                }
            };
        }
    }


}