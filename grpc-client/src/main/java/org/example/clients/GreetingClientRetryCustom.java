package org.example.clients;

import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.example.greeting.GreetingRequest;
import org.example.greeting.GreetingResponse;
import org.example.greeting.GreetingServiceGrpc;

import java.util.concurrent.TimeUnit;

public class GreetingClientRetryCustom {
    public static void GreetingClientWithDeadlineRetry(ManagedChannel channel, int maxRetries, int initialBackoffMillis) {
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

    private static boolean isRetryable(StatusRuntimeException e) {
        Status.Code statusCode = e.getStatus().getCode();
        // Check if the error code is retryable
        return switch (statusCode) {
                case UNAVAILABLE, DEADLINE_EXCEEDED, ABORTED -> true;
            default -> false;
        };
    }

}
