package com.example.reactivespring.configuration;

import com.example.reactivespring.handlers.CompanyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private CompanyHandler _companyHandler;
    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route()
          .path("company", this::companyRoutes)
          .build();
    }

    @Bean
    public RouterFunction<ServerResponse> companyRoutes() {
        return RouterFunctions.route()
          .GET("overview/parallel", serverRequest -> _companyHandler.GetOverviewParallel())
          .GET("overview/sequential", serverRequest -> _companyHandler.GetOverviewSequential())
          .GET("overview/stream", serverRequest -> _companyHandler.GetStream())
          .POST("", _companyHandler::SaveCompany)
          .build();
    }
}
