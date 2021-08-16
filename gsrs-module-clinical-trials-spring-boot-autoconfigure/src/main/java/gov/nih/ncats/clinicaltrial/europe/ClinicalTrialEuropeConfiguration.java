package gov.nih.ncats.clinicaltrial.europe;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@Import(ClinicalTrialEuropeStarterEntityRegistrar.class)
public class ClinicalTrialEuropeConfiguration {
}