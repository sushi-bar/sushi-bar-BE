package org.enricogiurin.sushibar.controller;

import org.enricogiurin.sushibar.bo.RegistrationBO;
import org.enricogiurin.sushibar.dto.RequestUserDTO;
import org.enricogiurin.sushibar.exception.SBException;
import org.enricogiurin.sushibar.util.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequestMapping("/registration")
@CrossOrigin
public class RegistrationController {
    @Autowired
    private RegistrationBO registrationBO;


    /*@PostMapping(produces = "application/json")
    public synchronized ResponseEntity<StringResponse> register(@RequestBody @Valid RequestUserDTO userDTO, HttpServletRequest request) {
        registrationBO.register(userDTO, request.getRequestURL().toString());
        return new ResponseEntity<>(StringResponse.of("User " + userDTO.getUsername() + " - registration pending"), HttpStatus.OK);
    }*/


    @PostMapping(produces = "application/json")
    public synchronized void register(@RequestBody @Valid RequestUserDTO userDTO, HttpServletRequest request, HttpServletResponse response) {
        registrationBO.register(userDTO, request.getRequestURL().toString());
        try {
            response.sendRedirect("/home");
        } catch (IOException e) {
            throw new SBException("Error while redirecting", e);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<StringResponse> confirmRegistration(@RequestParam String registrationCode, @RequestParam String email) {
        String username = registrationBO.confirmRegistration(registrationCode, email);
        return new ResponseEntity<>(StringResponse.of("User " + username + " has completed registration"), HttpStatus.OK);
    }


}
