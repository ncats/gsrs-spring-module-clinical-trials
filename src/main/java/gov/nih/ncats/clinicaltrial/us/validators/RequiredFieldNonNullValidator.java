package gov.nih.ncats.clinicaltrial.us.validators;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrialUS;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;

public class RequiredFieldNonNullValidator implements ValidatorPlugin<ClinicalTrialUS> {

    @Override
    public boolean supports(ClinicalTrialUS newValue, ClinicalTrialUS oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return true;
    }

    @Override
    public void validate(ClinicalTrialUS objnew, ClinicalTrialUS objold, ValidatorCallback callback) {
        if (objnew.getTitle() == null) {
            callback.addMessage(GinasProcessingMessage.WARNING_MESSAGE("null title"));
        } else if(objnew.getTitle().trim().isEmpty()) {
            callback.addMessage(GinasProcessingMessage.WARNING_MESSAGE("blank title"));
        }
    }
}
