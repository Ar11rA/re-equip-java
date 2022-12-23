package advanced;

import basics.Utility;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class OverflowExample {
    public static void main(String[] args) throws InterruptedException {
        Flux
          .create(fluxSink -> {
            for (int i = 1; i <= 500; i++) {
                fluxSink.next(i);
                System.out.println("Pushed: " + i);
            }
            fluxSink.complete();
        })
          .onBackpressureDrop() // onBackpressureDrop / Latest / Buffer
          .publishOn(Schedulers.boundedElastic())
          .doOnNext(i -> {
              try {
                  Thread.sleep(10);
              } catch (InterruptedException e) {
                  throw new RuntimeException(e);
              }
          })
          .subscribe(Utility.onNext());

        Thread.sleep(5000);
    }
}
