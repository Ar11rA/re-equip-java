package com.example.reactivespring.services;

import com.example.reactivespring.entities.Company;
import com.example.reactivespring.repositories.CompanyRepository;
import com.example.reactivespring.utilities.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository _companyRepository;

    // writing this way for delay simulation
    public ParallelFlux<Company> getOverviewParallel() {
        return Flux
          .range(1,4)
          .parallel()
          .runOn(Schedulers.parallel())
          .flatMap(id -> {
              TimeUtils.sleep(id);
              return _companyRepository.findById(id);
          });
    }

    public Flux<Company> getOverviewSequential() {
        return Flux
          .range(1,4)
          .flatMap(id -> {
              TimeUtils.sleep(id);
              return _companyRepository.findById(id);
          });
    }

    public Mono<Company> saveCompany(Company company) {
        return _companyRepository.save(company);
    }
}
