package com.company.consumer.correlation.misk;

public class Utils {

    private Utils() {
        // utility class, should not be instantiated
    }

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
