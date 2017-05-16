package com.baeldung.spring.cloud.hystrix.rest.producer;

import java.util.Set;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface GreetingController {
    @RequestMapping("/greeting/{username}")
    String greeting(@PathVariable("username") String username);

    @RequestMapping("/greetings")
    String greetingAll(@RequestParam("ids") Set<Integer> ids);

}
