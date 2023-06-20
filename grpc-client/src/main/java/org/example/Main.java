package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.clients.CalculatorClient;
import org.example.clients.GreetingClientRetryCustom;
import org.example.clients.GreetingClientSimple;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        int port = 50051;
        ManagedChannel channel = ManagedChannelBuilder
          .forAddress("localhost", port)
          .usePlaintext()
          .build();
        GreetingClientRetryCustom.GreetingClientWithDeadlineRetry(channel, 3, 1000);
        CalculatorClient.CalculatorClient(channel);
        GreetingClientSimple.GreetingClient(channel);
        channel.shutdown();
    }
}