package org.enricogiurin.sushibar.controller;

import org.enricogiurin.sushibar.Application;
import org.enricogiurin.sushibar.model.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class SecurityTest extends BaseControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private UserRepository userRepository;


    @Test
    public void wrongPassword() throws Exception {
        assertThat(userRepository.findByUsername("cicciopasticcio")).isPresent();
        mockMvc.perform(get("/simple").with(httpBasic("cicciopasticcio", "wrong"))
                .contentType(contentType))
                .andExpect(status().is(401));
    }

    @Test
    public void rightPassword() throws Exception {
        assertThat(userRepository.findByUsername("cicciopasticcio")).isPresent();
        mockMvc.perform(get("/simple").with(httpBasic("cicciopasticcio", "aaa"))
                .contentType(contentType))
                .andExpect(status().is(200));
    }


    @Test
    public void noAuth() throws Exception {
        mockMvc.perform(get("/simple")
                .contentType(contentType))
                .andExpect(status().is(401));
    }
}