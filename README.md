# Modern Java

1. Setting up a Java 19 spring-boot 3 project
2. Understanding Reactive Java
3. Setting up a Java 19 spring flux project
4. Understanding latest Java updates
5. Building sample services

## Why?

### Why Java 19?

- Java 19 comes with a lot of new features such as structured concurrency, vector API and preview to virtual threads
- Spring 3 comes with minimum Java 17 recommendation 

### Why explore Reactive Java?

#### Advantages

1. Cleaner code, more concise
2. Easier to scale (pipe any operation)
3. Better error handling
4. Event-driven inspired -> plays well with streams (Kafka, RabbitMQ,etc)
5. Easier retry and backpressure handling

#### Disadvantages

1. Can become more memory intensive in some cases
2. Somewhat steep learning curve
3. Debugging doesn't become easier

## Difference between Servlet and WebFlux

| Servlet  | WebFlux  |   
|---|---|
| Tomcat server  | Netty Server  | 
| Blocking IO  | Non blocking and asynchronous  |
| JDBC and JPA  | R2DBC |
| List and Optional  | Flux and Mono |
| Assertions and mocks  | Step verifier for unit tests |
| Easy debug tools  | Reactor and Hooks debug |
| Easy cloud integrations  | Specific cloud integrations |

## Sources

- Youtube: https://www.youtube.com/watch?v=f3acAsSZPhU&ab_channel=Devoxx
- Udemy: https://www.udemy.com/course/reactive-programming-in-modern-java-using-project-reactor/
- Project Reactor: https://projectreactor.io/docs/core/release/reference/
- Spring 6 and springboot 3: https://spring.io/projects/spring-boot

