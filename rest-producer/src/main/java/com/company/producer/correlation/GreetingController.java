package com.company.producer.correlation;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

public interface GreetingController {

    @RequestMapping("/greeting/{username}")
    String greeting(@PathVariable("username") String username);

    @RequestMapping("/greetings")
    String greetingAll(@RequestParam("ids") Set<Integer> ids);

}
