package org.enricogiurin.sushibar.controller;

import org.enricogiurin.sushibar.bo.RegistrationBO;
import org.enricogiurin.sushibar.dto.RequestUserDTO;
import org.enricogiurin.sushibar.util.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/registration")
@CrossOrigin
public class RegistrationController {
    @Autowired
    private RegistrationBO registrationBO;


    @PostMapping(produces = "application/json")
    public synchronized ResponseEntity<StringResponse> register(@RequestBody @Valid RequestUserDTO userDTO, HttpServletRequest request) {
        registrationBO.register(userDTO, request.getRequestURL().toString());
        //To complete your registration,</p><h3 style="text-align: center;">You need to confirm it.</h3><p>An e-mail has been sent to your address
        return new ResponseEntity<>(StringResponse.of("To complete your registration, You need to confirm it. An e-mail has been sent to your address"), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<StringResponse> confirmRegistration(@RequestParam String registrationCode, @RequestParam String email) {
        String username = registrationBO.confirmRegistration(registrationCode, email);
        return new ResponseEntity<>(StringResponse.of("User " + username + " has completed registration"), HttpStatus.OK);
    }


}
