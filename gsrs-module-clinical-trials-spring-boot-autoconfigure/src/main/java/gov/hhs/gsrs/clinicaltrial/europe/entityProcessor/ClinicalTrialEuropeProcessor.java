package gov.hhs.gsrs.clinicaltrial.europe.entityProcessor;

import gov.hhs.gsrs.clinicaltrial.europe.models.ClinicalTrialEurope;
import ix.core.EntityProcessor;

//@Component
public class ClinicalTrialEuropeProcessor implements EntityProcessor<ClinicalTrialEurope> {
    @Override
    public Class<ClinicalTrialEurope> getEntityClass() {
        return ClinicalTrialEurope.class;
    }

    @Override
    public void postLoad(ClinicalTrialEurope obj) throws FailProcessingException {
    }
}
