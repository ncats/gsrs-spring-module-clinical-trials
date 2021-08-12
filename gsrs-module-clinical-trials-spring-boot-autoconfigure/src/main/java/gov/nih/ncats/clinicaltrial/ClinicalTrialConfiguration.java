package gov.nih.ncats.clinicaltrial.clinicaltrial;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@Import(ClinicalTrialStarterEntityRegistrar.class)
public class ClinicalTrialConfiguration {
}