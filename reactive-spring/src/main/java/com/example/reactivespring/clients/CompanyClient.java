package com.example.reactivespring.clients;

import com.example.reactivespring.dto.CompanyExternalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CompanyClient {

    @Autowired
    private WebClient _webClient;

    public Mono<CompanyExternalDTO> getCompany(int id) {
        return _webClient
          .get()
          .uri("http://192.168.29.208:3000/company/random/{id}", id)
          .retrieve()
          .bodyToMono(CompanyExternalDTO.class);
    }
}
