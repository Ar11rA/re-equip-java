package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.config.LoggingInterceptor;
import org.example.services.CalculatorServiceImpl;
import org.example.services.GreetingServiceImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;
        Server server = ServerBuilder
          .forPort(port)
          .addService(new GreetingServiceImpl())
          .addService(new CalculatorServiceImpl())
          .intercept(new LoggingInterceptor())
          .build();
        server.start();
        System.out.println("Server started on port " + port);
        server.awaitTermination();
    }
}