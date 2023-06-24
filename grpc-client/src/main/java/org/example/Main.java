package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.clients.CalculatorClient;
import org.example.clients.GreetingClientRetryCustom;
import org.example.clients.GreetingClientSimple;
import org.example.config.AuthInterceptor;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        int port = 50051;
        ManagedChannel simpleChannel = ManagedChannelBuilder
          .forAddress("localhost", port)
          .usePlaintext()
          .intercept(new AuthInterceptor())
          .build();
        GreetingClientRetryCustom.GreetingClientWithDeadlineRetry(simpleChannel, 3, 1000);
        CalculatorClient.CalculatorClient(simpleChannel);
        GreetingClientSimple.GreetingClient(simpleChannel);
        simpleChannel.shutdown();
    }
}