package basics;

import reactor.core.publisher.Mono;

public class MonoExample {
    public static void main(String[] args) {
        Mono
                .just(1)
                .map(curr -> curr * 2)
                .subscribe(
                        Utility.onNext(),
                        Utility.onError(),
                        Utility.onComplete()
                );
    }
}
