package self.study.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.study.example.entities.Company;
import self.study.example.repositories.CompanyRepository;
import self.study.example.utilities.TimeUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
public class CompanyService {
    @Autowired
    private CompanyRepository _companyRepository;

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
}
