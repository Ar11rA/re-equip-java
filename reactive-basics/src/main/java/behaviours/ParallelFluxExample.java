package behaviours;

import basics.Utility;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Date;

public class ParallelFluxExample {
    private static boolean isPrime(int number) {
//        System.out.println(new Date() + ":" + number + ":" + Thread.currentThread().getName());
        int ctr = 0;
        if (number == 1) {
            return false;
        }
        if (number == 2) {
            return true;
        }
        for (int i = 2; i <= number / 2; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (number % i == 0) {
                ctr++;
            }
        }
        if (number == 100 || number == 3) {
            System.out.println(new Date() + ":" + number + ":" + Thread.currentThread().getName());
        }
        return ctr == 0;
    }

    public static void main(String[] args) {
        System.out.println("Normal: " + new Date());
        Flux
          .range(1, 100)
          .filter(ParallelFluxExample::isPrime)
          .subscribe();
        System.out.println("Normal: " + new Date());

        System.out.println("Parallel: " + new Date());
        Flux
          .range(1, 100)
          .parallel(3) // for cpu intensive tasks, use BoundedElastic for n/w or db calls
          .runOn(Schedulers.parallel())
          .filter(ParallelFluxExample::isPrime)
          .subscribe();
        System.out.println("Parallel: " + new Date());

        // to see the complete execution
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
