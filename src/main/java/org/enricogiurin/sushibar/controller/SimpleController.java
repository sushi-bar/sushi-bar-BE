package org.enricogiurin.sushibar.controller;

import org.enricogiurin.sushibar.util.StringResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class SimpleController {

    @GetMapping(value = "/simple", produces = "application/json")
    public ResponseEntity<StringResponse> confirmRegistration() {
        return new ResponseEntity<>(StringResponse.of("simple"), HttpStatus.OK);
    }


}
