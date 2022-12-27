package basics;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.Date;

public class FluxExample {
    public static Flux<Integer> getNumbers() {
        System.out.println("Invoking Get Numbers...");
        return Flux
                .fromIterable(Arrays.asList(1, 2, 3, 4))
                .map(i -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return i * 2;
                });
    }

    public static void main(String[] args) {
        getNumbers()
                .subscribe(Utility.onNext());
    }
}
