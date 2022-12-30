package self.study.example.services;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import self.study.example.clients.CompanyClient;
import self.study.example.entities.Company;
import self.study.example.repositories.CompanyRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepository _companyRepository;
    @Mock
    private CompanyClient _companyClient;

    @InjectMocks
    private CompanyService _companyService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        _companyRepository = mock(CompanyRepository.class);
        _companyClient = mock(CompanyClient.class);
        _companyService = new CompanyService(_companyRepository, _companyClient);
    }
    @Test
    void getOverviewSeq_Success() {
        Company company = new Company();
        company.setId(1);
        company.setName("ABC");
        Mockito.when(_companyRepository.findById(anyInt())).thenReturn(Optional.of(company));
        List<Company> companies = _companyService.getOverview();
        Assertions.assertEquals(4, companies.size());
    }
}
