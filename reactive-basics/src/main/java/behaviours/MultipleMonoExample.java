package behaviours;

import basics.Utility;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;

import java.util.Date;

public class MultipleMonoExample {

    private static int[] times = {2000, 10000, 1000, 4000};

    private static Mono<String> greet(int index) {
        System.out.println("Invoking Greet...");
        return Mono
                .fromSupplier(() -> {
                    try {
                        Thread.sleep(times[index - 1]);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Inside Mono...");
                    return "Hello! " + index;
                }).map(String::toUpperCase);
    }

    public static void main(String[] args) {
        System.out.println(new Date());
        greet(1)
                .subscribe(Utility.onNext());;
        greet(2)
                .subscribe(Utility.onNext());
        greet(3)
                .subscribe(Utility.onNext());
        greet(4)
                .subscribe(Utility.onNext());;
        System.out.println(new Date());

        System.out.println(new Date());
        Mono<String> m1 = greet(1);
        Mono<String> m2 = greet(2);
        Mono<String> m3 = greet(3);
        Mono<String> m4 = greet(4);
        Mono<Tuple4<String, String, String, String>> d = Mono
                .zip(m1, m2, m3, m4);
        d.subscribe(
                (i) -> {
                    System.out.println(i.getT1());
                    System.out.println(i.getT2());
                    System.out.println(i.getT3());
                    System.out.println(i.getT4());
                })
        ;
        System.out.println(new Date());
    }
}
