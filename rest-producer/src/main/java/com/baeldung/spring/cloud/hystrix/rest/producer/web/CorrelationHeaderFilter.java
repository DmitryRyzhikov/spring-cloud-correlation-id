package com.baeldung.spring.cloud.hystrix.rest.producer.web;


import com.baeldung.spring.cloud.hystrix.rest.producer.misk.CorrelationIdStorage;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
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

        System.out.println("Producer. CorrelationHeaderFilter entry point");

        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String currentCorrId = httpServletRequest.getHeader(CorrelationIdStorage.CORRELATION_ID_HEADER);

        if (currentCorrId == null) {
            currentCorrId = UUID.randomUUID().toString();
            System.out.println("Producer. No correlationId found in Header. Generated : " + currentCorrId);
        } else {
            System.out.println("Producer. Found correlationId in Header : " + currentCorrId);
        }

        CorrelationIdStorage.setId(currentCorrId);

        filterChain.doFilter(httpServletRequest, servletResponse);
    }


    @Override
    public void destroy() {
    }

}