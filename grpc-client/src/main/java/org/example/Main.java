package org.example;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
import org.example.calculator.*;
import org.example.greeting.GreetingRequest;
import org.example.greeting.GreetingResponse;
import org.example.greeting.GreetingResponses;
import org.example.greeting.GreetingServiceGrpc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class Main {

    private static void GreetingClientWithDeadlineRetry(ManagedChannel channel, int maxRetries, int initialBackoffMillis) {
        int retryCount = 1;
        int backoffMillis = initialBackoffMillis;

        while (retryCount <= maxRetries) {
            GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);
            try {
                GreetingResponse response = stub
                  .withDeadline(Deadline.after(2, TimeUnit.SECONDS))
                  .greetWithDeadlineAndRetry(GreetingRequest.newBuilder()
                    .setFirstName("John")
                    .setLastName("Doe")
                    .build());
                System.out.println(response.getGreeting());
                return;
            } catch (StatusRuntimeException exception) {
                System.out.println("Exception: " + exception.getStatus());
                if (exception.getStatus().getCode().equals(Status.Code.DEADLINE_EXCEEDED)) {
                    System.out.println("Timeout!!!");

                    // Check if it's appropriate to retry
                    if (!isRetryable(exception) || retryCount >= maxRetries) {
                        System.out.println("Maximum retries reached or non-retryable error. Aborting.");
                        return;
                    }

                    // Exponential backoff before retrying
                    try {
                        System.out.println("Exponential backoff: " + backoffMillis + "with retry count: " + retryCount);
                        Thread.sleep(backoffMillis);
                    } catch (InterruptedException ignored) {
                    }

                    backoffMillis *= 2;
                    retryCount++;
                }
            }
        }
    }

    private static boolean isRetryable(StatusRuntimeException e) {
        Status.Code statusCode = e.getStatus().getCode();
        // Check if the error code is retryable
        return switch (statusCode) {
            case UNAVAILABLE, DEADLINE_EXCEEDED, ABORTED -> true;
            default -> false;
        };
    }

    private static void GreetingClient(ManagedChannel channel) throws InterruptedException {
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

    private static void CalculatorClient(ManagedChannel channel) throws InterruptedException {
        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(channel);
        StringBuilder sb = new StringBuilder();
        int input = 120;
        stub.getPrimeFactors(PrimeNumberDecompositionRequest.newBuilder()
          .setNumber(input)
          .build()).forEachRemaining(r -> sb.append(r.getPrimeFactor()).append("*"));
        sb.setLength(sb.length() - 1);
        System.out.println(input + ": " + sb);
        CalculatorServiceGrpc.CalculatorServiceStub normalStub = CalculatorServiceGrpc.newStub(channel);
        CountDownLatch latch = new CountDownLatch(1);
        List<Integer> inputs = List.of(12, 8, 20, 14, 29, 16);
        StreamObserver<ComputeAverageRequest> requestStream = normalStub.getAverage(new StreamObserver<ComputeAverageResponse>() {
            @Override
            public void onNext(ComputeAverageResponse value) {
                System.out.println(value.getAverage());
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
        inputs.forEach(i -> requestStream.onNext(ComputeAverageRequest.newBuilder()
          .setNumber(i)
          .build()));
        requestStream.onCompleted();
        latch.await(10, java.util.concurrent.TimeUnit.SECONDS);

        StreamObserver<FindMaximumRequest> maxRequestStream = normalStub.getMax(new StreamObserver<FindMaximumResponse>() {
            @Override
            public void onNext(FindMaximumResponse value) {
                System.out.println("Current max: " + value.getMaximum());
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
        inputs.forEach(i -> maxRequestStream.onNext(FindMaximumRequest.newBuilder()
          .setNumber(i)
          .build()));
        maxRequestStream.onCompleted();
        latch.await(10, java.util.concurrent.TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 50051;
        ManagedChannel channel = ManagedChannelBuilder
          .forAddress("localhost", port)
          .usePlaintext()
          .build();
        GreetingClient(channel);
        CalculatorClient(channel);
        GreetingClientWithDeadlineRetry(channel, 3, 1000);
        channel.shutdown();
    }
}