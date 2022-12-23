package advanced;

import basics.Utility;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;

public class RepeatRetryExample {
    public static void main(String[] args) {
        Flux
          .range(1, 3)
          .repeat(1) // .repeat(() -> condition)
          .subscribe(Utility.onNext());

        Flux
          .range(5, 2)
          .map(i -> {
              double temp = (Math.random() * (10)) + 1;
              if (temp <= 6) {
                  System.out.println("Returning value for: " + i);
                  return i;
              } else {
                  System.out.println("Throwing error for: " + i);
                  throw new RuntimeException();
              }
          })
          .retry(5)
//          .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(3)))
          .subscribe(Utility.onNext());
    }
}
