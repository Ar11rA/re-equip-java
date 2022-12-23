package basics;

import java.util.function.Consumer;

public class Utility {
    public static Consumer<Object> onNext() {
        return System.out::println;
    }

    public static Consumer<Throwable> onError() {
        return err -> System.out.println("Error in processing: " + err.getMessage());
    }

    public static Runnable onComplete() {
        return () -> System.out.println("Completed!");
    }
}
