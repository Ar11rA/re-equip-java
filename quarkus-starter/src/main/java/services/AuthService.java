package services;

import clients.AuthClient;
import dto.LoginRequest;
import dto.TokenResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

@ApplicationScoped
public class AuthService {
    private final AuthClient authClient;
    @ConfigProperty(name = "auth.keycloak.client.id")
    String clientId;
    @ConfigProperty(name = "auth.keycloak.client.secret")
    String clientSecret;
    @ConfigProperty(name = "auth.keycloak.client.grant_type")
    String grantType;
    @ConfigProperty(name = "auth.keycloak.client.realm")
    String realm;


    public AuthService(@RestClient AuthClient authClient) {
        this.authClient = authClient;
    }

    public TokenResponse login(LoginRequest loginRequest) {
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.add("username", loginRequest.getUsername());
        params.add("grant_type", grantType);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("password", loginRequest.getPassword());
        return authClient.authenticate(realm, params);
    }

}
