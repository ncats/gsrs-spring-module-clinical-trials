package gov.nih.ncats.clinicaltrial.us.entityProcessor;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrialUS;
import ix.core.EntityProcessor;

//@Component
public class ClinicalTrialProcessor implements EntityProcessor<ClinicalTrialUS> {
    @Override
    public Class<ClinicalTrialUS> getEntityClass() {
        return ClinicalTrialUS.class;
    }

    @Override
    public void postLoad(ClinicalTrialUS obj) throws FailProcessingException {
        System.out.println("post Load ClincalTrial " + obj);
    }
}
