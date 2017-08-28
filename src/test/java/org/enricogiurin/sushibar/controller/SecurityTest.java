package org.enricogiurin.sushibar.controller;

import org.enricogiurin.sushibar.Application;
import org.enricogiurin.sushibar.model.User;
import org.enricogiurin.sushibar.model.UserRepository;
import org.enricogiurin.sushibar.util.Role;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by enrico on 7/16/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class SecurityTest extends BaseControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() throws Exception {
        super.setup();
        List<User> users = new ArrayList<>();
        users.add(new User("aa", "a@a.org", true, "", true, "", Role.ROLE_USER));
        userRepository.save(users);
    }

    @After
    public void after() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void wrongPassword() throws Exception {
        mockMvc.perform(get("/api/users").with(httpBasic("aa", "wrong"))
                .contentType(contentType))
                .andExpect(status().is(401));
    }

    @Test
    public void noAuth() throws Exception {
        mockMvc.perform(get("/api/users")
                .contentType(contentType))
                .andExpect(status().is(401));
    }
}