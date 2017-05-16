package com.baeldung.spring.cloud.hystrix.rest.producer;

import java.util.Set;

import com.baeldung.spring.cloud.hystrix.rest.producer.misk.CorrelationIdStorage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingControllerImpl implements GreetingController {
    @Override
    public String greeting(@PathVariable("username") String username) {
        System.out.println("Producer access. Should get correlation ID from consumer in case if accessed " +
                "not directly : " + CorrelationIdStorage.getId());

        return String.format("Hello %s!\n", username);
    }


    @Override
    public String greetingAll(@RequestParam("ids") Set<Integer> ids) {
        return null;
    }
}
