package com.baeldung.spring.cloud.hystrix.rest.consumer;

import com.baeldung.spring.cloud.hystrix.rest.consumer.misk.Utils;
import com.baeldung.spring.cloud.hystrix.rest.consumer.web.CorrelationIdAddInterceptor;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class GreetingService {

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new CorrelationIdAddInterceptor());
        restTemplate.setInterceptors(interceptors);

    }

    public String getGreeting(String username) {
        Utils.logThreadDetails("Consumer-service");
        return getGreetingWithHystrix(username);
    }


    @HystrixCommand(fallbackMethod = "defaultGreeting")
    public String getGreetingWithHystrix(String username) {

        Utils.logThreadDetails("Consumer-service-with-hystrix-annotation");

        return restTemplate.getForObject("http://localhost:9090/greeting/{username}", String.class, username);
    }


    private String defaultGreeting(String username) {
        return "Hello User!";
    }
}
