package gov.hhs.gsrs.clinicaltrial.us.events;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gsrs.events.AbstractEntityUpdatedEvent;

public class ClinicalTrialUSUpdateEvent extends AbstractEntityUpdatedEvent<ClinicalTrialUS> {
    public ClinicalTrialUSUpdateEvent(ClinicalTrialUS source) {
        super(source);
    }
}

