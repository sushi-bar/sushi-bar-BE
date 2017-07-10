package org.enricogiurin.sushibar.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.enricogiurin.sushibar.model.UserRepository;
import org.enricogiurin.sushibar.po.User;
import org.enricogiurin.sushibar.util.EmailSender;
import org.enricogiurin.sushibar.util.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public StringResponse register(@RequestBody User user) {
        org.enricogiurin.sushibar.model.User newUser = new org.enricogiurin.sushibar.model.User();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        userRepository.save(newUser);

        emailSender.sendSimpleMessage("enricogiurin@gmail.com", "registration to sushibar", "Dear " + user.getUsername() +
                " you are registered to sushibar");
        return new StringResponse("User "+user.getUsername()+ " is registered");
    }
}
