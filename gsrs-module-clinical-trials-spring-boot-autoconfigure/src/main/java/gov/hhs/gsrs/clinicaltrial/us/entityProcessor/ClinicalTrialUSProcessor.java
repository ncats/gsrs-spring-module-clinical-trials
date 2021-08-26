package gov.hhs.gsrs.clinicaltrial.us.entityProcessor;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import ix.core.EntityProcessor;

//@Component
public class ClinicalTrialUSProcessor implements EntityProcessor<ClinicalTrialUS> {
    @Override
    public Class<ClinicalTrialUS> getEntityClass() {
        return ClinicalTrialUS.class;
    }

    @Override
    public void postLoad(ClinicalTrialUS obj) throws FailProcessingException {
        System.out.println("post Load ClincalTrialUS " + obj);
    }
}
