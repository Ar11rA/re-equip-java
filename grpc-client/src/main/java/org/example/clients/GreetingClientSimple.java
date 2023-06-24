package org.example.clients;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import org.example.greeting.GreetingRequest;
import org.example.greeting.GreetingResponse;
import org.example.greeting.GreetingResponses;
import org.example.greeting.GreetingServiceGrpc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GreetingClientSimple {
    public static void GreetingClient(ManagedChannel channel) throws InterruptedException {
        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);
        GreetingResponse response = stub.greet(GreetingRequest.newBuilder()
          .setFirstName("John")
          .setLastName("Doe")
          .build());
        System.out.println(response.getGreeting());
        List<String> greetings = new ArrayList<>();
        stub.greetMany(GreetingRequest.newBuilder()
            .setFirstName("John")
            .setLastName("Doe")
            .setLimit(5)
            .build())
          .forEachRemaining(r -> greetings.add(r.getGreeting()));
        System.out.println(greetings);
        List<GreetingRequest> requests = new ArrayList<>();
        requests.add(GreetingRequest.newBuilder()
          .setFirstName("John")
          .setLastName("Doe")
          .build());
        requests.add(GreetingRequest.newBuilder()
          .setFirstName("Jane")
          .setLastName("Doe")
          .build());
        requests.add(GreetingRequest.newBuilder()
          .setFirstName("Jack")
          .setLastName("Doe")
          .build());
        List<String> responses = new ArrayList<>();
        GreetingServiceGrpc.GreetingServiceStub normalStub = GreetingServiceGrpc.newStub(channel);
        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<GreetingRequest> greetDiffRequestStream = normalStub.greetDifferent(new StreamObserver<GreetingResponses>() {
            @Override
            public void onNext(GreetingResponses response) {
                responses.addAll(response.getGreetingList());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        });
        requests.forEach(greetDiffRequestStream::onNext);
        greetDiffRequestStream.onCompleted();
        latch.await();
        System.out.println(responses);
        StreamObserver<GreetingRequest> greetOneByOneRequestStream = normalStub.greetOneByOne(new StreamObserver<GreetingResponse>() {
            @Override
            public void onNext(GreetingResponse value) {
                System.out.println(value.getGreeting());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Server is done!");
                latch.countDown();
            }
        });
        requests.forEach(greetOneByOneRequestStream::onNext);
        latch.await();
    }
}
