package org.enricogiurin.sushibar.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.enricogiurin.sushibar.component.EmailSender;
import org.enricogiurin.sushibar.exception.SBException;
import org.enricogiurin.sushibar.model.User;
import org.enricogiurin.sushibar.model.UserRepository;
import org.enricogiurin.sushibar.po.RequestUserDTO;
import org.enricogiurin.sushibar.util.Role;
import org.enricogiurin.sushibar.util.StringResponse;
import org.enricogiurin.sushibar.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by enrico on 7/8/17.
 */
@RestController
@CrossOrigin
public class RegistrationController {

    @Autowired
    ConfigurableApplicationContext context;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/registration" , produces = "application/json")
    @Transactional
    public ResponseEntity<StringResponse> register(@RequestBody RequestUserDTO userDTO, HttpServletRequest request) {
        userRepository.findByEmail(userDTO.getEmail())
                .ifPresent(user -> {
                    throw new SBException("email " + userDTO.getEmail() + " already present");
                });
        userRepository.findByUsername(userDTO.getUsername())
                .ifPresent(user -> {
                    throw new SBException("username " + userDTO.getUsername() + " already present");
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
        String url = Utils.buildURL(request.getRequestURL().toString(), userDTO.getEmail(), confirmationCode);
        emailSender.sendEmail(userDTO, url);

        return new ResponseEntity<>(StringResponse.of("User " + userDTO.getUsername() + " - registration pending"), HttpStatus.OK);
        //return new StringResponse("User " + userDTO.getUsername() + " - registration pending");
    }

    @GetMapping(value = "/registration" , produces = "application/json")
    public ResponseEntity<StringResponse> confirmRegistration(@RequestParam String registrationCode, @RequestParam String email) {
        User user = userRepository.findByEmailAndConfirmationCode(email, registrationCode)
                .orElseThrow(() -> new RuntimeException(email + " not found"));
        user.setConfirmed(true);
        user.setEnabled(true);
        userRepository.save(user);
        return new ResponseEntity<>(StringResponse.of("User " + user.getUsername() + " has completed registration"), HttpStatus.OK);
    }


}
