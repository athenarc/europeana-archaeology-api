package gr.dcu.europeana.arch.config;

import gr.dcu.europeana.arch.domain.Settings;
import gr.dcu.europeana.arch.domain.entity.SettingEntity;
import gr.dcu.europeana.arch.repository.SettingRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Configuration
public class AppConfig {

    // @Value("${mailgun.domainName}")
    // private String mailgunDomainName;
    
    // @Value("${mailgun.apiKey}")
    // private String mailgunApiKey;
    
    // @Value("${mailgun.sender}")
    // private String mailgunSender;
    
    // @Value("${mailgun.recipients}")
    // private String mailgunRecipients;
    
    // @Value("${api.baseUrl}")
    // private String apiBaseUrl;

    // @Value("${dashboard.url}")
    // private String dashboardUrl;

    public static final String PROJECT_NAME = "Europeana Archaeology";
    public static final String SERVICE_NAME = "Mapping Tool";

    /**
     * The application settings object
     *
     * @return The application settings as read from the database
     */
    @Bean
    public Settings settings(SettingRepository settingRepository) {
        final Map<String, String> keyValueMap = settingRepository
                .findAll()
                .stream()
                .collect(Collectors.toMap(SettingEntity::getKey, SettingEntity::getValue));

        final String changeMeValue = "please.change.me";

        return new Settings(
                keyValueMap.getOrDefault(SettingEntity.MAILGUN_DOMAIN_NAME, changeMeValue),
                keyValueMap.getOrDefault(SettingEntity.MAILGUN_API_KEY, changeMeValue),
                keyValueMap.getOrDefault(SettingEntity.MAILGUN_SENDER, changeMeValue)
        );
    }
}
