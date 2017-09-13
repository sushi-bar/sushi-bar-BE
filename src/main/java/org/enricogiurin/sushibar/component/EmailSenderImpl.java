package org.enricogiurin.sushibar.component;

import org.apache.velocity.app.VelocityEngine;
import org.enricogiurin.sushibar.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by enrico on 7/8/17.
 */
@Component
public class EmailSenderImpl implements EmailSender {
    public static final String SUBJECT = "registration to sushibar";
    public static final String EMAIL_FROM = "no-reply@sushibar.org";
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Override
    public void sendEmail(final UserDTO user, String url) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(user.getEmail());
                message.setSubject(SUBJECT);
                message.setFrom(EMAIL_FROM); // could be parameterized...
                Map model = new HashMap();
                model.put("user", user);
                model.put("url", url);
                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, "org/enricogiurin/sushibar/registration-confirmation.vm", model);
                message.setText(text, true);
            }
        };
        this.emailSender.send(preparator);
    }


}
