package org.enricogiurin.sushibar.component;

import org.enricogiurin.sushibar.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Component
public class EmailSenderImpl implements EmailSender {
    public static final String SUBJECT = "registration to sushibar";
    public static final String EMAIL_FROM = "no-reply@sushibar.org";
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendEmail(final UserDTO user, String url) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("no-reply@sushibar.org");
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("registration to sushibar");
            Context context = new Context();
            context.setVariable("username", user.getUsername());
            context.setVariable("url", url);
            String content = templateEngine.process("mailTemplate", context);
            messageHelper.setText(content, true);
        };
        emailSender.send(messagePreparator);
    }


}
