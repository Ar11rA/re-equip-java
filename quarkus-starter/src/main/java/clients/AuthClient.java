package clients;

import dto.res.TokenResponse;
import dto.res.TokenValidationResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;

@RegisterRestClient(configKey = "oauth-api")
public interface AuthClient {
    @POST
    @Path(value = "/realms/{realm}/protocol/openid-connect/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    TokenResponse authenticate(@PathParam("realm") String realm,
                               MultivaluedMap<String, String> params);

    @POST
    @Path(value = "/realms/{realm}/protocol/openid-connect/token/introspect")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    TokenValidationResponse validate(@PathParam("realm") String realm,
                                         MultivaluedMap<String, String> params);
}
