package self.study.example.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import self.study.example.dto.CompanyExternalDTO;

import java.util.concurrent.CompletableFuture;

@Component
public class CompanyClient {

    @Autowired
    private RestTemplate _restTemplate;

    @Async
    public CompletableFuture<CompanyExternalDTO> getCompany(int id) {
        String companyUrl = "http://192.168.29.208:3000/company/random/" + id;
        CompanyExternalDTO response = _restTemplate.getForObject(companyUrl, CompanyExternalDTO.class);
        return CompletableFuture.completedFuture(response);
    }
}