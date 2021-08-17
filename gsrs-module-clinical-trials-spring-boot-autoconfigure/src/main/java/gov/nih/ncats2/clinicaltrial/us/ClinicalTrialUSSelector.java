package gov.nih.ncats2.clinicaltrial.us;

import gov.nih.ncats2.clinicaltrial.us.controllers.ClinicalTrialUSController;
import gov.nih.ncats2.clinicaltrial.us.services.ClinicalTrialUSLegacySearchService;
import gov.nih.ncats2.clinicaltrial.us.services.ClinicalTrialUSEntityService;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class ClinicalTrialUSSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                ClinicalTrialUSEntityService.class.getName(),
                ClinicalTrialUSLegacySearchService.class.getName(),
                ClinicalTrialUSController.class.getName()
        };
    }
}