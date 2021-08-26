package gov.hhs.gsrs.clinicaltrial.us.events;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gsrs.events.AbstractEntityCreatedEvent;

public class ClinicalTrialCreatedEvent extends AbstractEntityCreatedEvent<ClinicalTrialUS> {
    public ClinicalTrialCreatedEvent(ClinicalTrialUS source) {
        super(source);
    }
}
