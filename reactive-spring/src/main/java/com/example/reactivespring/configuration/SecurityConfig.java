package com.example.reactivespring.configuration;

import com.example.reactivespring.interceptors.JWTAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private static final String[] AUTH_WHITELIST = {
      "/v3/api-docs/**",
      "/swagger-ui/**",
      "/docs",
      "/info/**"
    };

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http
          .addFilterAfter(new JWTAuthFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
          .csrf().disable()
          .httpBasic().disable();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }

}
