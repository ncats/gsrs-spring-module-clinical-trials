package gov.nih.ncats.clinicaltrial.us.events;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrialUS;
import gsrs.events.AbstractEntityUpdatedEvent;

public class ClinicalTrialUSUpdateEvent extends AbstractEntityUpdatedEvent<ClinicalTrialUS> {
    public ClinicalTrialUSUpdateEvent(ClinicalTrialUS source) {
        super(source);
        System.out.println("INSIDE ClinicalTrialUSUpdateEvent");
    }
}

