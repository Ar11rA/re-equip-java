package advanced;

import basics.Utility;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shared.ExamplePOJO;

import java.time.Duration;

public class OperatorsExample {
    public static void main(String[] args) {
        // 1. handle
        Flux
          .range(1, 10)
          .handle((elem, synchronousSink) -> {
              // filter
              if (elem % 2 == 0) {
                  synchronousSink.next(elem + "abc");
              }
              // map
          })
          .subscribe(Utility.onNext());

        // 2. limitRate
        Flux
          .range(1, 1000)
          .limitRate(5, 3)
          .take(10)
          .subscribe(Utility.onNext());

        // 3. timeout
        Flux
          .just(4, 2, 3, 5)
          .map(i -> {
              try {
                  Thread.sleep(i * 1000);
              } catch (InterruptedException e) {
                  throw new RuntimeException(e);
              }
              return i * 2;
          })
          .timeout(Duration.ofSeconds(1), Mono.just(10))
          .subscribe(Utility.onNext(), Utility.onError(), Utility.onComplete());

        // 4. flatmap
        // w/o flatmap
        Flux
          .range(1,5)
          .map(i -> getExampleListOfObjects(i))
          .subscribe(Utility.onNext());
        // with flatmap -> when you have return type as flux / mono
        Flux
          .range(1,5)
          .flatMap(i -> getExampleListOfObjects(i))
          .map(pojo -> pojo.field2)
          .subscribe(Utility.onNext());

    }

    private static Flux<ExamplePOJO> getExampleListOfObjects(int limit) {
        return Flux
          .range(1, limit)
          .map(i -> new ExamplePOJO(i, "example-" + i));
    }
}
