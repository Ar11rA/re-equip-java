package clients;

import dto.QuoteResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.concurrent.CompletionStage;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@RegisterRestClient(configKey = "quote-api")
public interface QuoteClient {
    @GET
    @Path("/random")
    CompletionStage<QuoteResponse> getAsync();

    @GET
    @Path("/random")
    QuoteResponse getSync();
}
