package resource;

import io.quarkus.logging.Log;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import services.GreetingService;

import static constants.AppConstants.GENERAL_ROLE;

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
    @RolesAllowed(GENERAL_ROLE)
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(String name) {
        Log.info("Request username: " + headers.getHeaderString("username"));
        return greetingService.greet(name);
    }
}