package gov.nih.ncats.clinicaltrial.europe;

import gov.nih.ncats.clinicaltrial.europe.controllers.ClinicalTrialEuropeController;
import gov.nih.ncats.clinicaltrial.europe.services.ClinicalTrialEuropeEntityService;
import gov.nih.ncats.clinicaltrial.europe.services.ClinicalTrialEuropeLegacySearchService;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class ClinicalTrialEuropeSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                ClinicalTrialEuropeEntityService.class.getName(),
                ClinicalTrialEuropeLegacySearchService.class.getName(),
                ClinicalTrialEuropeController.class.getName()
        };
    }
}