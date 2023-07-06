package services;

import clients.QuoteClient;
import dto.res.QuoteResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuoteService {

    private final QuoteClient quoteClient;

    public QuoteService(@RestClient QuoteClient quoteClient) {
        this.quoteClient = quoteClient;
    }

    public List<QuoteResponse> getQuoteAsync(int count) {
        List<CompletableFuture<QuoteResponse>> quoteFutures = IntStream.range(0, count)
          .mapToObj(i -> quoteClient.getAsync().toCompletableFuture())
          .toList();
        return quoteFutures
          .stream()
          .parallel()
          .map(CompletableFuture::join)
          .toList();
    }

    public List<QuoteResponse> getQuoteSync(int count) {
        List<QuoteResponse> quotes = IntStream.range(0, count)
          .mapToObj(i -> quoteClient.getSync())
          .toList();
        return quotes;
    }


}
