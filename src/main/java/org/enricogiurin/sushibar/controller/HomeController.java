package org.enricogiurin.sushibar.controller;

import org.enricogiurin.sushibar.util.StringResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/home")
@CrossOrigin
public class HomeController {

    @GetMapping(produces = "application/json")
    public ResponseEntity<StringResponse> message() {
        return new ResponseEntity<>(StringResponse.of("simple"), HttpStatus.OK);
    }


}
