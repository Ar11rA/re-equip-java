package com.example.reactivespring.services;

import com.example.reactivespring.clients.CompanyClient;
import com.example.reactivespring.dto.CompanyExternalDTO;
import com.example.reactivespring.entities.Company;
import com.example.reactivespring.repositories.CompanyRepository;
import com.example.reactivespring.utilities.TimeUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.publisher.SynchronousSink;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

@Service
public class CompanyService {

    private CompanyRepository _companyRepository;

    private CompanyClient _companyClient;

    public CompanyService(CompanyRepository _companyRepository, CompanyClient _companyClient) {
        this._companyRepository = _companyRepository;
        this._companyClient = _companyClient;
    }

    // writing this way for delay simulation
    public Flux<Company> getOverviewParallel() {
        return Flux
          .range(1, 4)
          .parallel()
          .runOn(Schedulers.boundedElastic())
          .flatMap(id -> {
              TimeUtils.sleep(id);
              return _companyRepository.findById(id);
          })
          .sequential()
          .log();
    }

    public Flux<Company> getOverviewSequential() {
        return Flux
          .range(1, 4)
          .flatMap(id -> {
              TimeUtils.sleep(id);
              return _companyRepository.findById(id);
          });
    }

    public Mono<Company> saveCompany(Company company) {
        return _companyRepository.save(company);
    }

    public Flux<CompanyExternalDTO> getCompaniesExternalZip() {
        Mono<CompanyExternalDTO> m1 = _companyClient.getCompany(1);
        Mono<CompanyExternalDTO> m2 = _companyClient.getCompany(2);
        Mono<CompanyExternalDTO> m3 = _companyClient.getCompany(3);
        Mono<CompanyExternalDTO> m4 = _companyClient.getCompany(4);
        return Flux
          .zip(m1, m2, m3, m4)
          .flatMap(i -> Flux.just(i.getT1(), i.getT2(), i.getT3(), i.getT4()));
    }

    public Flux<CompanyExternalDTO> getCompaniesExternalMerge() {
        Mono<CompanyExternalDTO> m1 = _companyClient.getCompany(1);
        Mono<CompanyExternalDTO> m2 = _companyClient.getCompany(2);
        Mono<CompanyExternalDTO> m3 = _companyClient.getCompany(3);
        Mono<CompanyExternalDTO> m4 = _companyClient.getCompany(4);
        return Flux
          .merge(m1, m2, m3, m4);
    }


    // Example
    public ParallelFlux<CompanyExternalDTO> getCompaniesExternalParallel(int limit) {
        return Flux
          .range(1, limit)
          .parallel()
          .runOn(Schedulers.boundedElastic())
          .flatMap(_companyClient::getCompany);
    }

    public Flux<CompanyExternalDTO> getCompaniesExternal(int limit) {
        return Flux
          .range(1, limit)
          .flatMap(_companyClient::getCompany);
    }

    public Flux<CompanyExternalDTO> getCompaniesExternalSeq(int limit) {
        BiFunction<Integer, SynchronousSink<CompanyExternalDTO>, Integer> generator = (state, sink) -> {
            Mono<CompanyExternalDTO> companyExternalDTOMono = _companyClient.getCompany(state);
            try {
                // Example to show sequential calls 1 by 1
                sink.next(companyExternalDTOMono.toFuture().get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            if (state == limit) {
                sink.complete();
            }
            return state + 1;
        };
        Callable<Integer> state = () -> 1;
        return Flux.generate(state, generator);
    }
}
