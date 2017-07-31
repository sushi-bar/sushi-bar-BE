package org.enricogiurin.sushibar.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.enricogiurin.sushibar.component.EmailSender;
import org.enricogiurin.sushibar.model.User;
import org.enricogiurin.sushibar.model.UserRepository;
import org.enricogiurin.sushibar.po.RequestUserDTO;
import org.enricogiurin.sushibar.util.Role;
import org.enricogiurin.sushibar.util.StringResponse;
import org.enricogiurin.sushibar.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by enrico on 7/8/17.
 */
@RestController
public class RegistrationController {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private UserRepository userRepository;


    @PostMapping(value = "/registration" , produces = "application/json")
    @Secured(value = {Role.ROLE_ANONYMOUS})
    @Transactional
    public StringResponse register(@RequestBody RequestUserDTO userDTO) {
        userRepository.findByEmail(userDTO.getEmail())
                .ifPresent(user -> {
                    throw new RuntimeException("email " + userDTO.getEmail() + " already present");
                });
        userRepository.findByUsername(userDTO.getUsername())
                .ifPresent(user -> {
                    throw new RuntimeException("username " + userDTO.getUsername() + " already present");
                });

        final String confirmationCode = RandomStringUtils.random(10, true, true);

        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(userDTO.getPassword());
        newUser.setConfirmationCode(confirmationCode);
        newUser.setRole(Role.ROLE_USER);

        userRepository.save(newUser);
        //TODO - fix this url
        String url = Utils.buildURL("http://localhost:8080/registration", userDTO.getEmail(), confirmationCode);
        emailSender.sendEmail(userDTO, url);
        return new StringResponse("User " + userDTO.getUsername() + " - registration pending");
    }

    @GetMapping(value = "/registration" , produces = "application/json")
    @Secured(value = {Role.ROLE_ANONYMOUS})
    public StringResponse confirmRegistration(@RequestParam String registrationCode,  @RequestParam String email) {
        User user = userRepository.findByEmailAndConfirmationCode(email, registrationCode)
                .orElseThrow(() -> new RuntimeException(email + " not found"));

        user.setConfirmed(true);
        user.setEnabled(true);
        userRepository.save(user);
        return new StringResponse("User "+user.getUsername()+ " is registered");
    }


}
