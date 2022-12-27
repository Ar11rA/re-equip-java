import basics.FluxExample;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ExampleTest {

    @Test
    void fluxExampleTest_Success() {
        Flux<Integer> getNumbersFlux = FluxExample.getNumbers();
        StepVerifier.create(getNumbersFlux)
          .expectNext(2)
          .expectNextCount(3)
          .verifyComplete();
    }
}
