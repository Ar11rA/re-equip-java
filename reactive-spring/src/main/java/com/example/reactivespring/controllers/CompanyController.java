package com.example.reactivespring.controllers;

import com.example.reactivespring.entities.Company;
import com.example.reactivespring.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("company")
public class CompanyController {

    @Autowired
    private CompanyRepository _companyRepository;
    @GetMapping("/overview")
    public Flux<Company> GetOverview() {
        return _companyRepository.findAll();
    }

    @PostMapping("")
    public Mono<Company> SaveCompany(@RequestBody() Company company) {
        return _companyRepository.save(company);
    }
}
