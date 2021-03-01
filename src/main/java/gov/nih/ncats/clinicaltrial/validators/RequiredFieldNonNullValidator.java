package gov.nih.ncats.clinicaltrial.validators;

import gov.nih.ncats.clinicaltrial.models.ClinicalTrial;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;

public class RequiredFieldNonNullValidator implements ValidatorPlugin<ClinicalTrial> {

    @Override
    public boolean supports(ClinicalTrial newValue, ClinicalTrial oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return true;
    }

    @Override
    public void validate(ClinicalTrial objnew, ClinicalTrial objold, ValidatorCallback callback) {
        if (objnew.getTitle() == null) {
            callback.addMessage(GinasProcessingMessage.WARNING_MESSAGE("null title"));
        } else if(objnew.getTitle().trim().isEmpty()) {
            callback.addMessage(GinasProcessingMessage.WARNING_MESSAGE("blank title"));
        }
    }
}
