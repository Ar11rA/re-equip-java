package resource;

import services.GreetingService;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;

@Path("/hello")
public class GreetingResource {
    private final GreetingService greetingService;

    @Context
    HttpHeaders headers;

    public GreetingResource(GreetingService greetingService) {
        this.greetingService = greetingService;
    }
}