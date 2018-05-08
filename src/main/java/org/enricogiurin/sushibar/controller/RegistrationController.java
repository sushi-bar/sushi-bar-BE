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
@CrossOrigin
public class RegistrationController {
    @Autowired
    private RegistrationBO registrationBO;


    @PostMapping(value = "/registration", produces = "application/json")
    public synchronized ResponseEntity<StringResponse> register(@RequestBody @Valid RequestUserDTO userDTO, HttpServletRequest request) {
        registrationBO.register(userDTO, request.getRequestURL().toString());
        return new ResponseEntity<>(StringResponse.of("User " + userDTO.getUsername() + " - registration pending"), HttpStatus.OK);
    }

    @GetMapping(value = "/registration" , produces = "application/json")
    public ResponseEntity<StringResponse> confirmRegistration(@RequestParam String registrationCode, @RequestParam String email) {
        String username = registrationBO.confirmRegistration(registrationCode, email);
        return new ResponseEntity<>(StringResponse.of("User " + username + " has completed registration"), HttpStatus.OK);
    }


}
