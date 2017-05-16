package com.baeldung.spring.cloud.hystrix.rest.consumer.web;


import com.baeldung.spring.cloud.hystrix.rest.consumer.misk.CorrelationIdStorage;
import com.baeldung.spring.cloud.hystrix.rest.consumer.misk.Utils;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;


/**
 * CorrelationHeaderFilter
 */
@Component
public class CorrelationHeaderFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String currentCorrId = httpServletRequest.getHeader(CorrelationIdStorage.CORRELATION_ID_HEADER);

        if (currentCorrId == null) {
            currentCorrId = UUID.randomUUID().toString();
            System.out.println("Consumer. No correlationId found in Header. Generated : " + currentCorrId);
        } else {
            System.out.println("Consumer. Found correlationId in Header : " + currentCorrId);
        }

        CorrelationIdStorage.setId(currentCorrId);

        Utils.logThreadDetails("Consumer-filter");

        filterChain.doFilter(httpServletRequest, servletResponse);
    }


    @Override
    public void destroy() {
    }

}