package services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GreetingServiceTest {

    private static GreetingService greetingService;

    @BeforeAll
    public static void setup() {
        greetingService = new GreetingService();
    }

    @Test
    public void testGreetSuccess() {
        String greeting = greetingService.greet("abc");
        Assertions.assertEquals("hello abc", greeting);
    }
}
