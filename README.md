# Modern Java

1. Setting up a Java 19 spring-boot 3 project
2. Understanding Reactive Java
3. Setting up a Java 19 spring flux project
4. Setting up grpc servers in gradle and maven
5. Setting up grpc clients
6. Understanding latest Java updates
7. Building sample micro services

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

#### Difference between Servlet and WebFlux

| Servlet  | WebFlux  |   
|---|---|
| Tomcat server  | Netty Server  | 
| Blocking IO  | Non blocking and asynchronous  |
| JDBC and JPA  | R2DBC |
| List and Optional  | Flux and Mono |
| Assertions and mocks  | Step verifier for unit tests |
| Easy debug tools  | Reactor and Hooks debug |
| Easy cloud integrations  | Specific cloud integrations |

### Why explore Grpc?

- High performance and efficiency with efficient network communication.
- Language-agnostic and platform-independent communication.
- Strongly typed contracts with Protocol Buffers for better interoperability.
- Support for bi-directional and streaming communication.
- Automatic code generation for simplified development.
- Built-in support for authentication and encryption.
- Growing ecosystem and integration with popular frameworks.
- Backward and forward compatibility with evolving API contracts.

#### Topics

| Topic                               | Description                                                                                                                                                             |
|-------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| gRPC                                 | An open-source high-performance RPC (Remote Procedure Call) framework developed by Google.                                                                             |
| Protocol Buffers (protobuf)         | A language-agnostic data serialization format used by gRPC to define message structures and service contracts.                                                         |
| Unary RPC                           | A type of RPC where the client sends a single request to the server and receives a single response in return.                                                          |
| Streaming RPC                       | A type of RPC where the client and server can send and receive multiple messages over a single connection.                                                              |
| Server-side Streaming               | A streaming RPC where the client sends a request and the server responds with a stream of messages.                                                                    |
| Client-side Streaming               | A streaming RPC where the client sends a stream of messages and the server responds with a single response.                                                            |
| Bidirectional Streaming             | A streaming RPC where the client and server can both send and receive streams of messages concurrently.                                                                 |
| Error Handling and Status Codes      | Handling and propagating errors between gRPC client and server, and the use of status codes to indicate the success or failure of an RPC call.                            |
| Deadlines and Timeout                | Setting deadlines and timeouts to limit the maximum duration of an RPC call, ensuring timely responses and preventing excessive wait times.                              |
| Interceptors                        | Middleware-like components that intercept and process RPC calls on the server-side or client-side, allowing cross-cutting concerns to be applied uniformly.             |
| SSL/TLS and Transport Security       | Securing gRPC communications using SSL/TLS encryption and authentication mechanisms, ensuring data privacy and integrity.                                               |
| Load Balancing                      | Distributing client requests across multiple server instances to achieve higher availability, scalability, and improved performance.                                     |
| Service Contracts and API Design     | Designing gRPC service contracts, defining messages, methods, and service interfaces to facilitate communication between clients and servers.                             |
| Code Generation                     | Automatically generating client and server code from protobuf service definitions, reducing manual effort and ensuring consistency in communication.                     |
| Error Handling and Retry Mechanisms  | Implementing error handling strategies and retry mechanisms to handle transient failures and improve the reliability of RPC calls.                                       |
| Testing gRPC Services                | Unit testing and integration testing of gRPC services using appropriate testing frameworks and techniques.                                                             |
| Performance Tuning and Optimization  | Optimizing gRPC performance by tweaking settings, managing connections, and fine-tuning configuration parameters for better throughput and latency.                    |
| Integration with Service Mesh        | Integrating gRPC-based services with service mesh frameworks like Istio or Linkerd for enhanced traffic management, observability, and security capabilities.          |
| Cross-language Interoperability     | Interoperability between different programming languages using gRPC, allowing services written in different languages to communicate seamlessly.                          |


## Sources

- Youtube: https://www.youtube.com/watch?v=f3acAsSZPhU&ab_channel=Devoxx
- Udemy: https://www.udemy.com/course/reactive-programming-in-modern-java-using-project-reactor/
- Project Reactor: https://projectreactor.io/docs/core/release/reference/
- Spring 6 and springboot 3: https://spring.io/projects/spring-boot

