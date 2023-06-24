package org.example.services;

import io.grpc.stub.StreamObserver;
import org.example.greeting.GreetingRequest;
import org.example.greeting.GreetingResponse;
import org.example.greeting.GreetingResponses;
import org.example.greeting.GreetingServiceGrpc;

public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
    @Override
    public void greet(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        String finalGreeting = String.format("Hi %s %s!", request.getFirstName(), request.getLastName());
        GreetingResponse response = GreetingResponse.newBuilder()
          .setGreeting(finalGreeting)
          .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void greetMany(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        String finalGreeting = String.format("Hi %s %s!", request.getFirstName(), request.getLastName());
        for (int i = 0; i < request.getLimit(); i++) {
            GreetingResponse response = GreetingResponse.newBuilder()
              .setGreeting(finalGreeting)
              .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<GreetingRequest> greetDifferent(StreamObserver<GreetingResponses> responseObserver) {
        GreetingResponses.Builder responses = GreetingResponses.newBuilder();
        StreamObserver<GreetingRequest> stream = new StreamObserver<GreetingRequest>() {
            @Override
            public void onNext(GreetingRequest request) {
                System.out.println("Received request: " + request);
                responses.addGreeting(String.format("Hi %s %s!", request.getFirstName(), request.getLastName()));
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(responses.build());
                responseObserver.onCompleted();
            }
        };
        return stream;
    }

    @Override
    public StreamObserver<GreetingRequest> greetOneByOne(StreamObserver<GreetingResponse> responseObserver) {
        StreamObserver<GreetingRequest> stream = new StreamObserver<GreetingRequest>() {
            @Override
            public void onNext(GreetingRequest value) {
                String finalGreeting = String.format("Hi %s %s!", value.getFirstName(), value.getLastName());
                GreetingResponse response = GreetingResponse.newBuilder()
                  .setGreeting(finalGreeting)
                  .build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
        return stream;
    }

    @Override
    public void greetWithDeadlineAndRetry(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        int randomDelayInSeconds = (int) (Math.random() * 5);
        System.out.println("Sleeping for " + randomDelayInSeconds + " seconds");
        try {
            Thread.sleep(randomDelayInSeconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String finalGreeting = String.format("Hi %s %s!", request.getFirstName(), request.getLastName());
        GreetingResponse response = GreetingResponse.newBuilder()
          .setGreeting(finalGreeting)
          .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
