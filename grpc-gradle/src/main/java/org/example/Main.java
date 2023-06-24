package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.config.DatabaseConfiguration;
import org.example.config.DynamoConfiguration;
import org.example.config.LoggingInterceptor;
import org.example.services.CalculatorServiceImpl;
import org.example.services.EmployeeServiceImpl;
import org.example.services.GreetingServiceImpl;
import org.example.services.ItemServiceImpl;
import org.hibernate.Session;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.io.IOException;
import java.net.URI;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Session dbSession = DatabaseConfiguration.getSession();
        DynamoDbClient dynamoDbClient = DynamoConfiguration.getClient();
        int port = 50051;
        Server server = ServerBuilder
          .forPort(port)
          .addService(new GreetingServiceImpl())
          .addService(new CalculatorServiceImpl())
          .addService(new EmployeeServiceImpl(dbSession))
          .addService(new ItemServiceImpl(dynamoDbClient))
          .intercept(new LoggingInterceptor())
          .build();
        server.start();
        System.out.println("Server started on port " + port);
        server.awaitTermination();
    }
}