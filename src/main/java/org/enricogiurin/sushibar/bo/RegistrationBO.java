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
        userRepository.findByUsernameOrEmail(userDTO.getUsername(), userDTO.getEmail())
                .ifPresent(user -> {
                    throw new SBException("username or email already present in the system");
                });

        final String confirmationCode = RandomStringUtils.random(10, true, true);

        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setConfirmationCode(confirmationCode);
        newUser.setRole(Role.ROLE_USER);

        userRepository.save(newUser);
        //TODO - fix this url
        String url = Utils.buildURL(requestURL, userDTO.getEmail(), confirmationCode);
        emailSender.sendEmail(userDTO, url);
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
