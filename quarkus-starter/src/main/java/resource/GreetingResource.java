package resource;

import services.GreetingService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {
    private final GreetingService greetingService;

    @Context
    HttpHeaders headers;

    public GreetingResource(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GET
    @Path("/{name}")
    @RolesAllowed("General")
    @Produces(MediaType.TEXT_PLAIN)
    public String greet(String name) {
        return this.greetingService.greet(name);
    }
}