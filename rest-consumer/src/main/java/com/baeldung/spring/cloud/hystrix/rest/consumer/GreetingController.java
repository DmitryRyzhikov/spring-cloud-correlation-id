package com.baeldung.spring.cloud.hystrix.rest.consumer;

import com.baeldung.spring.cloud.hystrix.rest.consumer.misk.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GreetingController {

    @Autowired
    private GreetingService greetingService;


    @RequestMapping("/get-greeting/{username}")
    public String getGreeting(Model model, @PathVariable("username") String username) {
        Utils.logThreadDetails("Consumer-controller");
        String greeting = greetingService.getGreeting(username);

        model.addAttribute("greeting", greeting);
        return "greeting-view";
    }
}
