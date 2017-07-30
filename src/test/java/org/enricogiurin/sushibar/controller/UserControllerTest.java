package org.enricogiurin.sushibar.controller;

import org.enricogiurin.sushibar.Application;
import org.enricogiurin.sushibar.model.Role;
import org.enricogiurin.sushibar.model.RoleRepository;
import org.enricogiurin.sushibar.model.User;
import org.enricogiurin.sushibar.model.UserRepository;
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
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by enrico on 7/16/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class UserControllerTest extends BaseControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setup() throws Exception {
        super.setup();


        List<User> users = new ArrayList<>();
        users.add(new User("aa", "aa@comp.org", "aaa", "", true, true));
        users.add(new User("bb", "bb@comp.org", "aaa", "", true, true));
        users.add(new User("notconfirmed", "notconfirmed@comp.org", "aaa", "", false, false));
        users.forEach(user -> user.setRoles(Collections.singleton(new Role(Role.ROLE_USER))));
        userRepository.save(users);
    }

    @After
    public void after() throws Exception {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void activeUsers() throws Exception {
        mockMvc.perform(get("/user").with(httpBasic("aa", "aaa"))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("aa")))
                .andExpect(jsonPath("$[0].email", is("aa@comp.org")))
                .andExpect(jsonPath("$[1].username", is("bb")))
                .andExpect(jsonPath("$[1].email", is("bb@comp.org")));
    }


}