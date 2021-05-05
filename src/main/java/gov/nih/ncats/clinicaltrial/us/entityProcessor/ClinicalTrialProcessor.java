package gov.nih.ncats.clinicaltrial.us.entityProcessor;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
import ix.core.EntityProcessor;

//@Component
public class ClinicalTrialProcessor implements EntityProcessor<ClinicalTrial> {
    @Override
    public Class<ClinicalTrial> getEntityClass() {
        return ClinicalTrial.class;
    }

    @Override
    public void postLoad(ClinicalTrial obj) throws FailProcessingException {
        System.out.println("post Load ClincalTrial " + obj);
    }
}
