package resource;

import clients.QuoteClient;
import dto.QuoteResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import services.GreetingService;

import io.quarkus.logging.Log;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;

import static constants.AppConstants.GENERAL_ROLE;

@Path("/hello")
public class GreetingResource {
    private final GreetingService greetingService;
    private final QuoteClient quoteClient;

    @Context
    HttpHeaders headers;

    public GreetingResource(GreetingService greetingService,
                            @RestClient QuoteClient quoteClient) {
        this.greetingService = greetingService;
        this.quoteClient = quoteClient;
    }

    @GET
    @Path("/{name}")
    @RolesAllowed(GENERAL_ROLE)
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(String name) {
        Log.info("Request username: " + headers.getHeaderString("username"));
        return greetingService.greet(name);
    }

    @GET
    @Path("/async/{count}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
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

    @GET
    @Path("/sync/{count}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public List<QuoteResponse> getQuoteSync(int count) {
        List<QuoteResponse> quotes = IntStream.range(0, count)
          .mapToObj(i -> quoteClient.getSync())
          .toList();
        return quotes;
    }
}