package org.enricogiurin.sushibar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.enricogiurin.sushibar.Application;
import org.enricogiurin.sushibar.TestUtils;
import org.enricogiurin.sushibar.model.Role;
import org.enricogiurin.sushibar.model.RoleRepository;
import org.enricogiurin.sushibar.model.UserRepository;
import org.enricogiurin.sushibar.po.RequestUserDTO;
import org.enricogiurin.sushibar.util.EmailSenderImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Address;
import javax.mail.internet.MimeMessage;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by enrico on 7/16/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class RegistrationControllerTest extends BaseControllerTest {

    private static String EMAIL_TO = "enrico@enricogiurin.org";
    private static String URL_REGISTRATION = "/registration";
    private static String USERNAME = "enrico";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    private GreenMail testSmtp;

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
        roleRepository.save(new Role(Role.ROLE_USER));
        testSmtp = new GreenMail(ServerSetupTest.SMTP);
        testSmtp.start();
    }


    @Test
    public void register() throws Exception {
        mockMvc.perform(
                post(URL_REGISTRATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new RequestUserDTO(USERNAME, EMAIL_TO, ""))))
                .andExpect(status().isOk());

        MimeMessage[] receivedMessages = testSmtp.getReceivedMessages();
        assertEquals(1, receivedMessages.length);
        MimeMessage message = receivedMessages[0];
        assertEquals(EmailSenderImpl.SUBJECT, message.getSubject());
        String body = GreenMailUtil.getBody(message).replaceAll("=\r?\n", "");
        Address to = message.getAllRecipients()[0];
        Address from = message.getFrom()[0];
        assertEquals(EMAIL_TO, to.toString());
        assertEquals(EmailSenderImpl.EMAIL_FROM, from.toString());
        String url = TestUtils.extractLink(body);
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

    }

    @After
    public void after() {
        testSmtp.stop();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

}