package gov.nih.ncats.clinicaltrial.entityProcessor;

import gov.nih.ncats.clinicaltrial.models.ClinicalTrial;
import ix.core.EntityProcessor;

//@Component
public class MyEntityProcessor implements EntityProcessor<ClinicalTrial> {
    @Override
    public Class<ClinicalTrial> getEntityClass() {
        return ClinicalTrial.class;
    }

    @Override
    public void postLoad(ClinicalTrial obj) throws FailProcessingException {
        System.out.println("post Load ClincalTrial " + obj);
    }
}
