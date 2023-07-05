package config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import javax.annotation.Priority;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constants.AppConstants.*;

@Provider
@Priority(1)
public class SecurityInterceptor implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    private boolean handleRoleCheck(DecodedJWT decodedToken, Method method) {
        String[] userRoles =
          decodedToken.getClaim(GROUPS_CLAIM).asArray(String.class);
        if (method.isAnnotationPresent(RolesAllowed.class)) {
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
        }
        return false;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();
        if (method.isAnnotationPresent(PermitAll.class)) {
            return;
        }

        if (method.isAnnotationPresent(DenyAll.class)) {
            requestContext.abortWith(Response.status(403).build());
            return;
        }
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();
        final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
        if (authorization == null) {
            requestContext.abortWith(Response.status(401).build());
            return;
        }
        final String token = authorization
          .get(0)
          .replaceFirst(String.format("%s ", AUTHENTICATION_SCHEME), "");
        try {
            DecodedJWT decodedToken = JWT.decode(token);
            if (!handleRoleCheck(decodedToken, method)) {
                requestContext.abortWith(Response.status(403).build());
                return;
            }
            requestContext.getHeaders().add(USERNAME_HEADER, decodedToken
              .getClaim(USERNAME_CLAIM)
              .toString());
        } catch (Exception exception) {
            requestContext.abortWith(Response.status(401).build());
        }
    }
}
