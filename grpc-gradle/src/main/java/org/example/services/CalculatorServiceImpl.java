package org.example.services;

import io.grpc.stub.StreamObserver;
import org.example.calculator.*;

import java.util.ArrayList;
import java.util.List;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {
    @Override
    public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {
        int sum = request.getNumbersList().stream().reduce(0, Integer::sum);
        responseObserver.onNext(SumResponse.newBuilder().setResult(sum).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getPrimeFactors(PrimeNumberDecompositionRequest request, StreamObserver<PrimeNumberDecompositionResponse> responseObserver) {
        long input = request.getNumber();
        long divisor = 2;
        while (input > 1) {
            if (input % divisor == 0) {
                input /= divisor;
                responseObserver.onNext(PrimeNumberDecompositionResponse.newBuilder().setPrimeFactor(divisor).build());
            } else {
                divisor++;
            }
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<ComputeAverageRequest> getAverage(StreamObserver<ComputeAverageResponse> responseObserver) {
        List<Integer> numbers = new ArrayList<>();
        StreamObserver<ComputeAverageRequest> stream = new StreamObserver<ComputeAverageRequest>() {
            @Override
            public void onNext(ComputeAverageRequest request) {
                System.out.println("Received request: " + request.getNumber());
                numbers.add(request.getNumber());
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                double average = numbers.stream().mapToInt(Integer::intValue).average().orElse(0);
                responseObserver.onNext(ComputeAverageResponse.newBuilder().setAverage(average).build());
                responseObserver.onCompleted();
            }
        };
        return stream;
    }

    @Override
    public StreamObserver<FindMaximumRequest> getMax(StreamObserver<FindMaximumResponse> responseObserver) {
        StreamObserver<FindMaximumRequest> stream = new StreamObserver<FindMaximumRequest>() {
            int max = -1;

            @Override
            public void onNext(FindMaximumRequest request) {
                if (request.getNumber() > max) {
                    max = request.getNumber();
                    responseObserver.onNext(FindMaximumResponse.newBuilder().setMaximum(max).build());
                }
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
    public void squareRoot(SquareRootRequest request, StreamObserver<SquareRootResponse> responseObserver) {
        if (request.getNumber() < 0) {
            responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
              .withDescription("The number being sent is not positive")
              .augmentDescription("Number sent: " + request.getNumber())
              .asRuntimeException()
            );
        } else {
            double numberRoot = Math.sqrt(request.getNumber());
            responseObserver.onNext(SquareRootResponse.newBuilder().setNumberRoot(numberRoot).build());
            responseObserver.onCompleted();
        }
    }
}
