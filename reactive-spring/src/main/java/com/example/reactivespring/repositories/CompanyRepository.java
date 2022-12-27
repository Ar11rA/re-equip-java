package com.example.reactivespring.repositories;

import com.example.reactivespring.entities.Company;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends ReactiveCrudRepository<Company, Integer> {
}
