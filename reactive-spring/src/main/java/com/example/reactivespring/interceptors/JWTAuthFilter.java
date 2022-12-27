package com.example.reactivespring.interceptors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class JWTAuthFilter implements WebFilter {

    @Override
    public Mono<Void> filter(
      ServerWebExchange exchange,
      WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        var authHeader = request
            .getHeaders()
            .get(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.empty();
        }
        return chain.filter(exchange);
    }
}
