package resource;

import dto.LoginRequest;
import dto.TokenResponse;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import services.AuthService;

@Path("/auth")
public class AuthResource {

    private final AuthService authService;

    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/login")
    public TokenResponse login(LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

}
