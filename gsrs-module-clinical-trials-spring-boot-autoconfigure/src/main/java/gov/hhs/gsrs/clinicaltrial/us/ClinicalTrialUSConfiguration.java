package gov.hhs.gsrs.clinicaltrial.us;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureAfter(DataJpaRepositoriesAutoConfiguration.class)
@Import(ClinicalTrialUSStarterEntityRegistrar.class)
public class ClinicalTrialUSConfiguration {
}
