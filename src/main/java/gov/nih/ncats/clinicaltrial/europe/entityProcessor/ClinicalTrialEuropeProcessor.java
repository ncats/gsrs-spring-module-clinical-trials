package gov.nih.ncats.clinicaltrial.europe.entityProcessor;

import gov.nih.ncats.clinicaltrial.europe.models.ClinicalTrialEurope;
import ix.core.EntityProcessor;

//@Component
public class ClinicalTrialEuropeProcessor implements EntityProcessor<ClinicalTrialEurope> {
    @Override
    public Class<ClinicalTrialEurope> getEntityClass() {
        return ClinicalTrialEurope.class;
    }

    @Override
    public void postLoad(ClinicalTrialEurope obj) throws FailProcessingException {
        System.out.println("post Load ClincalTrialEurope " + obj);
    }
}