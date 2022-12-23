package advanced;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotColdPublisherExample {
    public static void main(String[] args) throws InterruptedException {
        // cold subscriber -> 1 per user, hot publisher -> 1 for all users

        // 1. cold - let 2 different subscribers call
        Flux<Integer> sample1 = Flux
          .range(1, 5)
          .doOnSubscribe(i -> System.out.println("Received cold request!"))
          .delayElements(Duration.ofSeconds(2));

        sample1.subscribe((i) -> {
            System.out.println("Sub 1 received: " + i);
        });

        Thread.sleep(5000);

        sample1.subscribe((i) -> {
            System.out.println("Sub 2 received: " + i);
        });

        Thread.sleep(20000);

        // 2. hot - let 2 different subscribers call
        Flux<Integer> sample2 = Flux
          .range(1, 5)
          .doOnSubscribe(i -> System.out.println("Received hot request!"))
          .delayElements(Duration.ofSeconds(2))
          .share(); // -> .publish().refCount(1)

        sample2.subscribe((i) -> {
            System.out.println("Sub 1 received: " + i);
        });

        Thread.sleep(5000);

        sample2.subscribe((i) -> {
            System.out.println("Sub 2 received: " + i);
        });

        Thread.sleep(50000);
    }

}
