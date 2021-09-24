package gov.hhs.gsrs.clinicaltrial.europe;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({ClinicalTrialEuropeSelector.class, ClinicalTrialEuropeConfiguration.class})
public @interface EnableClinicalTrialEurope {
}