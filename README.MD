### Relevant Articles:
- [Implementing Correlation IDs in Spring Boot For Distributed Tracing](https://dzone.com/articles/implementing-correlation-ids-0)

# TABLE of CONTENTS
* [HOW IT WORKS](#HOW_IT_WORKS)
* [PRODUCER](#PRODUCER)
* [CONSUMER HYSTRIX](#CONSUMER)

## HOW IT WORKS <a name="HOW_IT_WORKS"/>
Producer and consumer are done as separate modules, but in real life it will be two communicating services. That is why
some files (like CorrelationHeaderFilter) added to both.
To check everything, both should be run (check below). Than call to consumer should be fired.
* This call intercepted by consumer CorrelationHeaderFilter. Filter checks, that Request doesn't contain header
"Correlation-Id", so it generates new UID, that will be used as correlation ID and put it to LocalThread variable
inside CorrelationIdStorage to be able access it in business logic.
* Consumer calls Producer via RestTemplate. We need to add CorrelationIdAddInterceptor interceptor to RestTemplate
that will be able to extract correlation ID from ThreadLocal and add it as header to request to Producer. Below is
full log of different processing stages in Consumer - from filter - to controller - to service

`
    Consumer. No correlationId found in Header. Generated : be1642c9-2291-43d8-871a-7132f4de12c0

    Consumer-filter. Thread ID [29] and thread name [http-nio-8080-exec-1]. Correlation ID [be1642c9-2291-43d8-871a-7132f4de12c0]

    Consumer-controller. Thread ID [29] and thread name [http-nio-8080-exec-1]. Correlation ID [be1642c9-2291-43d8-871a-7132f4de12c0]

    Consumer-service. Thread ID [29] and thread name [http-nio-8080-exec-1]. Correlation ID [be1642c9-2291-43d8-871a-7132f4de12c0]

    Consumer-service-with-hystrix-annotation. Thread ID [29] and thread name [http-nio-8080-exec-1]. Correlation ID [be1642c9-2291-43d8-871a-7132f4de12c0]

    Consumer. Passing correlation ID down the call chain in response : be1642c9-2291-43d8-871a-7132f4de12c0
`
* Producer accepts call from Consumer. This call intercepted by consumer CorrelationHeaderFilter. Filter checks,
that Request contains header "Correlation-Id", and if so put it to LocalThread variable inside CorrelationIdStorage
to be able access it in business logic.


    Producer. Found correlationId in Header : be1642c9-2291-43d8-871a-7132f4de12c0
    Producer. Passing correlation ID down the call chain in response : be1642c9-2291-43d8-871a-7132f4de12c0
    Producer access. Should get correlation ID from consumer in case if accessed not directly : be1642c9-2291-43d8-871a-7132f4de12c0

*IMPORTANT* Consumer uses @HystrixCommand annotation inside GreetingService. In case if method under this annotation
is called directly from controller, it will be called in separate Thread - so it WILL BE IMPOSSIBLE TO EXTRACT VALUE
FROM THREAD LOCAL VARIABLE WITH CORRELATION ID. To avoid this - use technique of calling annotated method from another
method inside service. Probably prob

## PRODUCER <a name="PRODUCER"/>
* To run producer
    - Run maven target: rest-producer [spring-boot:run]
* To check that producer is working


    curl http://localhost:9090/greeting/Cid
        Expected output:
    Hello Cid!


## CONSUMER HYSTRIX <a name="CONSUMER"/>
Consumer is the service that should access working producer via RestTemplate. Works via Hystrix - that
mean that in case if PRODUCER will not be available, Hystrix fallback method will work.

@EnableCircuitBreaker - will scan classpath for @HystrixCommand annotated methods.

* To run consumer
    - Run maven target: rest-consumer [spring-boot:run]
* To check that consumer is working type in browser:


    http://localhost:8080/get-greeting/Cid
    curl http://localhost:8080/get-greeting/Cid

    Expected output:
    Hello Cid!

* To check that Hystrix fallback is working - shut down PRODUCER and type in browser. Instead of greeting to Cid,
greeting to user will be shown.


    http://localhost:8080/get-greeting/Cid

    Expected output:
    Hello User!

* If we will start producer again, consumer will show greeting to Cid.
