package org.enricogiurin.sushibar.bo;

import org.apache.commons.lang3.RandomStringUtils;
import org.enricogiurin.sushibar.component.EmailSender;
import org.enricogiurin.sushibar.dto.RequestUserDTO;
import org.enricogiurin.sushibar.exception.SBException;
import org.enricogiurin.sushibar.model.User;
import org.enricogiurin.sushibar.model.UserRepository;
import org.enricogiurin.sushibar.util.Role;
import org.enricogiurin.sushibar.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.enricogiurin.sushibar.util.Role.ROLE_USER;

/**
 * Created by enrico on 7/8/17.
 */
@Service
public class RegistrationBO {

    @Autowired
    ConfigurableApplicationContext context;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RequestUserDTO userDTO, String requestURL) {
        //ugly workaround to avoid concurrency issues

        userRepository.findByUsernameOrEmail(userDTO.getUsername(), userDTO.getEmail())
                .ifPresent(user -> {
                    throw new SBException("username or email already present in the system");
                });

        final String confirmationCode = RandomStringUtils.random(10, true, true);
        User newUser = User.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .confirmationCode(confirmationCode)
                .role(ROLE_USER)
                .build();
        //TODO - fix this url
        String url = Utils.buildURL(requestURL, userDTO.getEmail(), confirmationCode);
        emailSender.sendEmail(userDTO, url);
        userRepository.save(newUser);
    }


    @Transactional
    public String confirmRegistration(String registrationCode, String email) {
        User user = userRepository.findByEmailAndConfirmationCode(email, registrationCode)
                .orElseThrow(() -> new RuntimeException(email + " not found"));
        user.setConfirmed(true);
        user.setEnabled(true);
        userRepository.save(user);
        return user.getUsername();
    }


}
