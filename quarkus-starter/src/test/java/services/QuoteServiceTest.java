package services;

import clients.QuoteClient;
import dto.QuoteResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class QuoteServiceTest {
    private static QuoteService quoteService;
    private static QuoteClient quoteClient;

    @BeforeAll
    public static void setup() {
        quoteClient = Mockito.mock(QuoteClient.class);
        quoteService = new QuoteService(quoteClient);
    }

    @Test
    public void listQuotesSuccess_10_quotes() {
        Mockito.when(quoteClient.getSync()).thenReturn(new QuoteResponse());
        List<QuoteResponse> quotes = quoteService.getQuoteSync(10);
        Assertions.assertEquals(10, quotes.size());
    }

}
