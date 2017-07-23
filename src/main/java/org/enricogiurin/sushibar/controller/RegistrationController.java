package org.enricogiurin.sushibar.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.enricogiurin.sushibar.model.User;
import org.enricogiurin.sushibar.model.UserRepository;
import org.enricogiurin.sushibar.po.RequestUserDTO;
import org.enricogiurin.sushibar.util.EmailSender;
import org.enricogiurin.sushibar.util.StringResponse;
import org.enricogiurin.sushibar.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public StringResponse register(@RequestBody RequestUserDTO user) {
        List<User> users = userRepository.findByEmail(user.getEmail());
        if (users.size() > 0) {
            throw new IllegalArgumentException("Email "+user.getEmail()+" is already present in the system!");
        }
        final String confirmationCode = RandomStringUtils.random(10, true, true);

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setConfirmationCode(confirmationCode);
        userRepository.save(newUser);
        //TODO - fix this url
        String url = Utils.buildURL("http://localhost:8080/registration", user.getEmail(), confirmationCode);

        emailSender.sendSimpleMessage("enricogiurin@gmail.com", "registration to sushibar", "Dear " + user.getUsername() +
                " click this link in order to resister to sushi-bar.\n" +url);
        return new StringResponse("User "+user.getUsername()+ " - registration pending");
    }

    @GetMapping(value = "/registration" , produces = "application/json")
    public StringResponse confirmRegistration(@RequestParam String registrationCode,  @RequestParam String email) {
        List<User> users = userRepository.findByEmailAndConfirmationCode(email, registrationCode);
        if (users.size() == 0) {
            throw new IllegalArgumentException("User not found in the system");
        }
        User user = users.get(0);
        user.setConfirmed(true);
        user.setEnabled(true);
        userRepository.save(user);
        return new StringResponse("User "+user.getUsername()+ " is registered");
    }


}
