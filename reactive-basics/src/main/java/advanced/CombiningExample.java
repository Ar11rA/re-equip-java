package advanced;

import basics.Utility;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class CombiningExample {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> f1 = Flux.just("a", "b").delayElements(Duration.ofMillis(700));
        Flux<String> f2 = Flux.just("c", "d").delayElements(Duration.ofMillis(800));
        // Flux<String> f3 = Flux.error(new RuntimeException("error"));

        // 1. concat
        Flux<String> concatenated = Flux.concat(f1, f2); // concatDelayError -> f3 error comes in end
        concatenated.subscribe(Utility.onNext());
        
        // 2. merge
        Flux<String> merged = Flux.merge(f1, f2);
        merged.subscribe(Utility.onNext());

        // 3. zip
        Flux<Tuple2<String, String>> zipped = Flux.zip(f1, f2);
        zipped.subscribe(Utility.onNext());

        // 4. zip with
        Flux<String> resultZipWith = f1.zipWith(f2, (a, b) -> a + b);
        resultZipWith.subscribe(Utility.onNext());

        Thread.sleep(5000);
    }
}
