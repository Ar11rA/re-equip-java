package resource;

import dto.res.QuoteResponse;
import services.QuoteService;

import java.util.List;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/quote")
public class QuoteResource {

    private final QuoteService quoteService;

    public QuoteResource(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GET
    @Path("/async/{count}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public List<QuoteResponse> getQuoteAsync(int count) {
        return this.quoteService.getQuoteAsync(count);
    }

    @GET
    @Path("/sync/{count}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public List<QuoteResponse> getQuoteSync(int count) {
        return this.quoteService.getQuoteSync(count);
    }
}