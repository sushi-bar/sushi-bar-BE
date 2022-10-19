package org.virtualsushibar.backend.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.virtualsushibar.backend.service.KafkaServiceProducer;

@RestController
@RequestMapping("/v1/kafka/publish")
@RequiredArgsConstructor
@Slf4j
public class PublishMessageController {
    private final KafkaServiceProducer kafkaServiceProducer;
    @RequestMapping(
            method = {RequestMethod.POST},
            produces = "application/json"
    )
    public ResponseEntity<String> publish(@RequestBody String message) {
        kafkaServiceProducer.sendMessage(message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
