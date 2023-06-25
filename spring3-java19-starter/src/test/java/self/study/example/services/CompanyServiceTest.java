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
import self.study.example.dto.CompanyExternalDTO;
import self.study.example.entities.Company;
import self.study.example.repositories.CompanyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
    void getOverviewDb_Success() {
        Company company = new Company();
        company.setId(1);
        company.setName("ABC");
        when(_companyRepository.findById(anyInt())).thenReturn(Optional.of(company));
        List<Company> companies = _companyService.getOverview();
        Assertions.assertEquals(4, companies.size());
    }
    @Test
    void saveCompanyTest() {
        Company company = new Company();
        company.setId(1);
        company.setName("ABC");
        when(_companyRepository.save(any(Company.class))).thenReturn(company);
        Company savedCompany = _companyService.saveCompany(company);
        Assertions.assertEquals(company, savedCompany);
    }
    @Test
    void getCompaniesExternalSeqTest() {
        CompanyExternalDTO company = new CompanyExternalDTO();
        when(_companyClient.getCompany(anyInt())).thenReturn(CompletableFuture.completedFuture(company));
        List<CompanyExternalDTO> companies = _companyService.getCompaniesExternalSeq(2);
        Assertions.assertNotNull(companies);
    }
    @Test()
    void getCompaniesExternalSeqTestError() {
        CompanyExternalDTO company = new CompanyExternalDTO();
        List<CompanyExternalDTO> companies = new ArrayList<>();
        CompletableFuture<CompanyExternalDTO> future = new CompletableFuture<>();
        future.completeExceptionally(new Exception("HTTP call failed!"));
        when(_companyClient.getCompany(anyInt())).thenReturn(future);
        try {

            companies  = _companyService.getCompaniesExternalSeq(2);
        }
        catch (RuntimeException e) {
            //System.out.println(e);
            System.out.println("catch");
            Assertions.assertEquals(0, companies.size());
        }
    }
    @Test
    void getCompaniesExternalParallelTest() {
        CompanyExternalDTO company = new CompanyExternalDTO();
        when(_companyClient.getCompany(anyInt())).thenReturn(CompletableFuture.completedFuture(company));
        List<CompanyExternalDTO> companies = _companyService.getCompaniesExternalParallel(2);
        Assertions.assertNotNull(companies);
    }
}
