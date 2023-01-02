package self.study.example.clients;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import self.study.example.dto.CompanyExternalDTO;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CompanyClientTests {
    @Mock
    private RestTemplate restTemplateMock;
    @InjectMocks
    private CompanyClient clientMock;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        restTemplateMock = mock(RestTemplate.class);
        clientMock = new CompanyClient(restTemplateMock);
    }

    @Test
    public void getCompany_Success() {
        CompanyExternalDTO companyExternalDTO = new CompanyExternalDTO();
        companyExternalDTO.setId("1");
        companyExternalDTO.setName("abc");

        when(restTemplateMock.getForObject(
          anyString(), eq(CompanyExternalDTO.class)
        )).thenReturn(companyExternalDTO);

        CompletableFuture<CompanyExternalDTO> response = clientMock.getCompany(1);
        try {
            Assertions.assertEquals("1", response.get().getId());
            Assertions.assertEquals("abc", response.get().getName());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
