package self.study.example.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
public class HttpClientConfig {

    @Bean
    public RestTemplate configure() {
        return new RestTemplate();
    }
}
