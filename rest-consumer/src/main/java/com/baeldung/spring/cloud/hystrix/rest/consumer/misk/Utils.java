package com.baeldung.spring.cloud.hystrix.rest.consumer.misk;

/**
 * Created by ryzhikov.d on 15.05.2017.
 */
public class Utils {

    public static void logThreadDetails(String client) {
        // block for tracking thread parameters and correlation ID
        long threadId = Thread.currentThread().getId();
        String threadName = Thread.currentThread().getName();
        System.out.printf(
                "%s. Thread ID [%s] and thread name [%s]. Correlation ID [%s]\n",
                client, threadId, threadName, CorrelationIdStorage.getId()
        );
    }

}
