package gr.dcu.europeana.arch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author vangelis
 */
@Slf4j
@SpringBootApplication
public class EuropeanaArchaelogyApplication extends SpringBootServletInitializer {
    
    public static void main(String[] args) {
        SpringApplication.run(EuropeanaArchaelogyApplication.class);
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
