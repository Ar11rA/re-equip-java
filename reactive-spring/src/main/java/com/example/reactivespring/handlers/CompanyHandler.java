package com.example.reactivespring.handlers;

import com.example.reactivespring.entities.Company;

import com.example.reactivespring.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;


@Service
public class CompanyHandler {

    @Autowired
    private CompanyService _companyService;
    public Mono<ServerResponse> GetOverviewParallel() {
        ParallelFlux<Company> companies = _companyService.getOverviewParallel();
        return ServerResponse.ok().body(companies, Company.class);
    }

    public Mono<ServerResponse> GetOverviewSequential() {
        Flux<Company> companies = _companyService.getOverviewSequential();
        return ServerResponse.ok().body(companies, Company.class);
    }

    public Mono<ServerResponse> GetStream() {
        Flux<Company> companies = _companyService.getOverviewSequential();
        return ServerResponse.ok()
          .contentType(MediaType.TEXT_EVENT_STREAM)
          .body(companies, Company.class);
    }

    public Mono<ServerResponse> SaveCompany(ServerRequest serverRequest)
    {   Mono<Company> company = serverRequest.bodyToMono(Company.class);
        Mono<Company> savedCompany = _companyService.saveCompany(company.block());
        return ServerResponse.ok().body(savedCompany, Company.class);
    }
}
