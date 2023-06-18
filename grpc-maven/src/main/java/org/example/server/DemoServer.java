package org.example.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.services.BankService;
import org.example.services.GreetService;

public class DemoServer {
    public static void run() throws Exception {
        Server server = ServerBuilder.forPort(8080)
          .addService(new BankService())
          .addService(new GreetService())
          .build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }
}
