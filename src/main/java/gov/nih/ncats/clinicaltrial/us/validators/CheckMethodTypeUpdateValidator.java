package gov.nih.ncats.clinicaltrial.us.validators;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;

public class CheckMethodTypeUpdateValidator implements ValidatorPlugin<ClinicalTrial> {

    @Override
    public boolean supports(ClinicalTrial newValue, ClinicalTrial oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return methodType == ValidatorConfig.METHOD_TYPE.CREATE;
    }

    @Override
    public void validate(ClinicalTrial objnew, ClinicalTrial objold, ValidatorCallback callback) {
        System.out.println("AM I A CREATE VALIDATOR?");
    }
}