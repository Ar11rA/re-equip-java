package behaviours;

import basics.Utility;
import reactor.core.publisher.Flux;

public class FluxSinksExample {
    public static void main(String[] args) {
        Flux.create(fluxSink -> {
            fluxSink.next(1);
            fluxSink.next(2);
            fluxSink.complete();
        }).subscribe(Utility.onNext());

        Flux.generate(synchronousSink -> {
            synchronousSink.next(3);
            // allowed to emit only 1 data -> synchronousSink.next(2); -> error
            synchronousSink.complete();
        }).subscribe(Utility.onNext());

        Flux.generate(() -> 1,
                (state, synchronousSink) -> {
            synchronousSink.next(4);
            // allowed to emit only 1 data -> synchronousSink.next(2); -> error
            if (state == 5) {
                synchronousSink.complete();
            }
            return state + 1;
        }).subscribe(Utility.onNext());
    }
}
