package com.baeldung.spring.cloud.hystrix.rest.producer.misk;


/**
 * Utility class which stores ThreadLocal (Request) correlation Id.
 */
public class CorrelationIdStorage {

    public static final String CORRELATION_ID_HEADER = "Correlation-Id";

    private static final ThreadLocal<String> id = new ThreadLocal<String>();


    public static void setId(String correlationId) {
        id.set(correlationId);
    }


    public static String getId() {
        return id.get();
    }
}
