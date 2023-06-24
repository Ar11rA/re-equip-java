package org.example.services;

import io.grpc.stub.StreamObserver;
import org.example.models.GreetRequest;
import org.example.models.GreetResponse;
import org.example.models.GreetServiceGrpc;

public class GreetService extends GreetServiceGrpc.GreetServiceImplBase {
    public GreetService() {
    }

    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        String greeting = "Hello " + request.getName();
        GreetResponse response = GreetResponse.newBuilder()
          .setGreeting(greeting)
          .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
