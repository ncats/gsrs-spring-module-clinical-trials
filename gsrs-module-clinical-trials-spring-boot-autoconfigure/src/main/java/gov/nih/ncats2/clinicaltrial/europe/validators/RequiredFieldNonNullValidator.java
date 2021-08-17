package gov.nih.ncats2.clinicaltrial.europe.validators;

import gov.nih.ncats2.clinicaltrial.europe.models.ClinicalTrialEurope;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;

public class RequiredFieldNonNullValidator implements ValidatorPlugin<ClinicalTrialEurope> {

    @Override
    public boolean supports(ClinicalTrialEurope newValue, ClinicalTrialEurope oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return true;
    }

    @Override
    public void validate(ClinicalTrialEurope objnew, ClinicalTrialEurope objold, ValidatorCallback callback) {
        if (objnew.getTitle() == null) {
            callback.addMessage(GinasProcessingMessage.WARNING_MESSAGE("null title"));
        } else if(objnew.getTitle().trim().isEmpty()) {
            callback.addMessage(GinasProcessingMessage.WARNING_MESSAGE("blank title"));
        }
    }
}
