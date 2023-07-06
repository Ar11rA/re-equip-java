package resource;

import dto.LoginRequest;
import dto.TokenResponse;
import services.AuthService;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/auth")
public class AuthResource {

    private final AuthService authService;

    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/login")
    @PermitAll
    public TokenResponse login(LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

}
