package org.virtualsushibar.backend.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.KafkaException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.virtualsushibar.backend.avro.Order;
import org.virtualsushibar.backend.service.OrderService;
import org.virtualsushibar.backend.service.impl.OrderServiceImpl;

@RestController
@RequestMapping("/v1/kafka/publish")
@RequiredArgsConstructor
@Slf4j
public class PublishMessageController {

    private final OrderServiceImpl orderService;
    @RequestMapping(
            method = {RequestMethod.POST},
            produces = "application/json"
    )
    public ResponseEntity<String> publish(@RequestBody Order order) {
        try {
            orderService.createOrder(order);
        }
        catch (KafkaException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
