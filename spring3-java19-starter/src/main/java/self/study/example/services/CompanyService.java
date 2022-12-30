package self.study.example.services;

import org.springframework.stereotype.Service;
import self.study.example.clients.CompanyClient;
import self.study.example.dto.CompanyExternalDTO;
import self.study.example.entities.Company;
import self.study.example.repositories.CompanyRepository;
import self.study.example.utilities.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
public class CompanyService {

    private CompanyRepository _companyRepository;

    private CompanyClient _companyClient;

    public CompanyService(CompanyRepository _companyRepository, CompanyClient _companyClient) {
        this._companyRepository = _companyRepository;
        this._companyClient = _companyClient;
    }

    // writing this way for delay simulation
    public List<Company> getOverview() {
        return IntStream
          .range(1, 5)
          .mapToObj(id -> {
              TimeUtils.sleep(id);
              return _companyRepository.findById(id);
          })
          .map(company -> company.orElseGet(Company::new))
          .collect(Collectors.toList());
    }

    public Company saveCompany(Company company) {
        return _companyRepository.save(company);
    }


    public List<CompanyExternalDTO> getCompaniesExternalSeq(int limit) {
        List<CompanyExternalDTO> companies = new ArrayList<>();
        for (int i = 1; i <= limit; i++) {
            CompanyExternalDTO company = null;
            try {
                company = _companyClient.getCompany(i).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            companies.add(company);
        }
        return companies;
    }

    public List<CompanyExternalDTO> getCompaniesExternalParallel(int limit) {
        List<CompletableFuture<CompanyExternalDTO>> companyFutures = new ArrayList<>();
        for (int i = 1; i <= limit; i++) {
            companyFutures.add(_companyClient.getCompany(i));
        }
        CompletableFuture.allOf(companyFutures.toArray(new CompletableFuture[companyFutures.size()])).join();
        return companyFutures.stream().parallel().map(CompletableFuture::join).toList();
    }
}
