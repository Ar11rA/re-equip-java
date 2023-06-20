package org.example.clients;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import org.example.calculator.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CalculatorClient {
    public static void CalculatorClient(ManagedChannel channel) throws InterruptedException {
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
}
