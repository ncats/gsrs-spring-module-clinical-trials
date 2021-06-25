package gov.nih.ncats.clinicaltrial.us.events;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrialUS;
import gsrs.events.AbstractEntityCreatedEvent;

public class ClinicalTrialCreatedEvent extends AbstractEntityCreatedEvent<ClinicalTrialUS> {
    public ClinicalTrialCreatedEvent(ClinicalTrialUS source) {
        super(source);
    }
}
