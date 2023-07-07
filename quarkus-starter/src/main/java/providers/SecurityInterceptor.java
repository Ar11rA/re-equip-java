package providers;

import dto.res.TokenValidationResponse;
import services.AuthService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.annotation.Priority;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;

import static constants.AppConstants.USERNAME_HEADER;
import static jakarta.ws.rs.core.Response.Status.FORBIDDEN;
import static jakarta.ws.rs.core.Response.Status.UNAUTHORIZED;
import static jakarta.ws.rs.core.Response.status;

@Provider
@Priority(1)
public class SecurityInterceptor implements ContainerRequestFilter {
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    @Context
    private ResourceInfo resourceInfo;
    @Inject
    AuthService authService;

    private boolean handleRoleCheck(TokenValidationResponse response,
                                    Method method) {
        List<String> userRoles = response.getGroups();
        RolesAllowed rolesAllowedAnnotation =
          method.getAnnotation(RolesAllowed.class);
        String[] rolesSet = rolesAllowedAnnotation.value();

        Map<String, Boolean> allowedRoles = new HashMap<>();
        for (String role : rolesSet) {
            allowedRoles.put(role, true);
        }
        for (String role : userRoles) {
            if (allowedRoles.containsKey(role)) {
                return true;
            }
        }

        return false;
    }

    private String extractToken(ContainerRequestContext requestContext) {
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();
        final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
        if (authorization == null) {
            return null;
        }
        return authorization
          .get(0)
          .replaceFirst(String.format("%s ", AUTHENTICATION_SCHEME), "");
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();

        // ignore authentication for permitted routes
        if (method.isAnnotationPresent(PermitAll.class)) {
            return;
        }

        // check for bearer token
        String token = extractToken(requestContext);
        if (token == null) {
            requestContext.abortWith(status(UNAUTHORIZED).build());
        }

        // validate token
        TokenValidationResponse validationResponse = authService.validate(token);
        if (!validationResponse.isActive()) {
            requestContext.abortWith(status(UNAUTHORIZED).build());
            return;
        }

        // check role
        if (!handleRoleCheck(validationResponse, method) &&
          method.isAnnotationPresent(RolesAllowed.class)) {
            requestContext.abortWith(status(FORBIDDEN).build());
            return;
        }

        requestContext
          .getHeaders()
          .add(USERNAME_HEADER, validationResponse.getUsername());
    }
}
