package gov.nih.ncats2.clinicaltrial.us;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({ClinicalTrialUSSelector.class, ClinicalTrialUSConfiguration.class})
public @interface EnableClinicalTrialUS {
}