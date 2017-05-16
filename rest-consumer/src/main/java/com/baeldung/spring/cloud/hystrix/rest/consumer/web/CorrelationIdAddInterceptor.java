package com.baeldung.spring.cloud.hystrix.rest.consumer.web;

import com.baeldung.spring.cloud.hystrix.rest.consumer.misk.CorrelationIdStorage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Intercepts requests to remote services done via RestTemplate and add correlations ID header with value
 * to such requests. Should be registered as RestTemplate interceptor
 * {@link com.baeldung.spring.cloud.hystrix.rest.consumer.GreetingService#init}
 */
@Component
public class CorrelationIdAddInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        System.out.println(
                "Consumer. Passing correlation ID down the call chain in response : "
                        + CorrelationIdStorage.getId() + "\n"
        );

        HttpHeaders headers = request.getHeaders();
        headers.add(CorrelationIdStorage.CORRELATION_ID_HEADER, CorrelationIdStorage.getId());
        return execution.execute(request, body);
    }
}