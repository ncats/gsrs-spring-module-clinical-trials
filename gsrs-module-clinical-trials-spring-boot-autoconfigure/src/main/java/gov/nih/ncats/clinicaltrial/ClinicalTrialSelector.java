package gov.nih.ncats.clinicaltrial.clinicaltrial;

import gov.nih.ncats.clinicaltrial.clinicaltrial.controllers.ClinicalTrialController;
import gov.nih.ncats.clinicaltrial.clinicaltrial.searcher.LegacyApplicationSearcher;
import gov.nih.ncats.clinicaltrial.clinicaltrial.services.ApplicationEntityService;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class ClinicalTrialSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                ClinicalTrialEntityService.class.getName(),
                LegacyClinicalTrialSearcher.class.getName(),
                ClinicalTrialController.class.getName()
        };
    }
}